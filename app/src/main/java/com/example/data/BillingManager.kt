package com.example.data

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.*
import com.example.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BillingManager(
    private val context: Context,
    private val repository: EnigmaRepository,
    private val coroutineScope: CoroutineScope
) {
    private var billingClient: BillingClient? = null
    var isClientConnected = false
        private set
    var isBillingServiceAvailable = true
        private set

    // Official Google Play Product IDs for your developer Console
    val PRO_MONTHLY_PRODUCT_ID = "pro_monthly"
    val PRO_QUARTERLY_PRODUCT_ID = "pro_quarterly"
    val PREMIUM_LIFETIME_PRODUCT_ID = "premium_lifetime"

    // Base plan IDs for alternative unified subscription setups
    val BASE_PLAN_MONTHLY = "monthly"
    val BASE_PLAN_QUARTERLY = "quarterly"

    // Fallback/standard prices
    var priceProMonthlyProvider: () -> Double = { 14.99 }
    var priceProQuarterlyProvider: () -> Double = { 29.99 }
    var pricePremiumProvider: () -> Double = { 49.99 }

    // Dynamic formatted prices queried directly from Google Play (e.g., "14,99 €", "29,99 €", "49,99 €")
    private val _formattedPrices = MutableStateFlow<Map<String, String>>(
        mapOf(
            PRO_MONTHLY_PRODUCT_ID to "14,99 €",
            PRO_QUARTERLY_PRODUCT_ID to "29,99 €",
            PREMIUM_LIFETIME_PRODUCT_ID to "49,99 €"
        )
    )
    val formattedPrices: StateFlow<Map<String, String>> = _formattedPrices.asStateFlow()

    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                handlePurchase(purchase)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            Log.d("BillingManager", "El usuario ha cancelado la compra de Google Play.")
        } else {
            Log.e("BillingManager", "Error en PurchasesUpdatedListener: ${billingResult.debugMessage}")
        }
    }

    init {
        initializeBillingClient()
    }

    private fun initializeBillingClient() {
        billingClient = BillingClient.newBuilder(context)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build())
            .build()

        startConnection()
    }

    fun startConnection(onSuccess: (() -> Unit)? = null) {
        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    isClientConnected = true
                    isBillingServiceAvailable = true
                    Log.d("BillingManager", "Conexión con Google Play Billing establecida con éxito.")
                    fetchProductPrices()
                    queryActivePurchases()
                    onSuccess?.invoke()
                } else if (billingResult.responseCode == BillingClient.BillingResponseCode.SERVICE_UNAVAILABLE ||
                    billingResult.responseCode == BillingClient.BillingResponseCode.SERVICE_DISCONNECTED ||
                    billingResult.responseCode == BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED
                ) {
                    isClientConnected = false
                    isBillingServiceAvailable = false
                    Log.w("BillingManager", "Billing service unavailable (normal in emulator/sandbox): ${billingResult.debugMessage}")
                } else {
                    Log.d("BillingManager", "Código de estado de Billing inesperado: ${billingResult.responseCode} - ${billingResult.debugMessage}")
                }
            }

            override fun onBillingServiceDisconnected() {
                isClientConnected = false
                Log.d("BillingManager", "Servicio desconectado de Google Play. Intentando reconectar...")
            }
        })
    }

    // Query real product details to extract Play Store formatted prices
    fun fetchProductPrices() {
        if (!isClientConnected) return

        val subsList = listOf(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(PRO_MONTHLY_PRODUCT_ID)
                .setProductType(BillingClient.ProductType.SUBS)
                .build(),
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(PRO_QUARTERLY_PRODUCT_ID)
                .setProductType(BillingClient.ProductType.SUBS)
                .build()
        )

        val inAppList = listOf(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(PREMIUM_LIFETIME_PRODUCT_ID)
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        )

        // Query subscriptions
        billingClient?.queryProductDetailsAsync(
            QueryProductDetailsParams.newBuilder().setProductList(subsList).build()
        ) { result, queryProductDetailsResult ->
            val productDetailsList = queryProductDetailsResult.productDetailsList
            if (result.responseCode == BillingClient.BillingResponseCode.OK && productDetailsList != null) {
                val updated = _formattedPrices.value.toMutableMap()
                for (details in productDetailsList) {
                    val price = details.subscriptionOfferDetails?.firstOrNull()
                        ?.pricingPhases?.pricingPhaseList?.firstOrNull()?.formattedPrice
                    if (!price.isNullOrBlank()) {
                        updated[details.productId] = price
                    }
                }
                _formattedPrices.value = updated
            }
        }

        // Query in-app
        billingClient?.queryProductDetailsAsync(
            QueryProductDetailsParams.newBuilder().setProductList(inAppList).build()
        ) { result, queryProductDetailsResult ->
            val productDetailsList = queryProductDetailsResult.productDetailsList
            if (result.responseCode == BillingClient.BillingResponseCode.OK && productDetailsList != null) {
                val updated = _formattedPrices.value.toMutableMap()
                for (details in productDetailsList) {
                    val price = details.oneTimePurchaseOfferDetails?.formattedPrice
                    if (!price.isNullOrBlank()) {
                        updated[details.productId] = price
                    }
                }
                _formattedPrices.value = updated
            }
        }
    }

    // Live query of active user subscriptions and entitlements (checks and revokes expired)
    fun queryActivePurchases(onFinished: ((Boolean) -> Unit)? = null) {
        if (!isClientConnected) {
            startConnection { queryActivePurchases(onFinished) }
            return
        }

        // Query active Subscriptions (Pro Plan)
        billingClient?.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.SUBS)
                .build()
        ) { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                val activePurchases = purchases.filter { it.purchaseState == Purchase.PurchaseState.PURCHASED }
                val activeQuarterly = activePurchases.firstOrNull { it.products.contains(PRO_QUARTERLY_PRODUCT_ID) }
                val activeMonthly = activePurchases.firstOrNull { it.products.contains(PRO_MONTHLY_PRODUCT_ID) }

                if (activeQuarterly != null) {
                    coroutineScope.launch {
                        repository.activatePremiumSubscription(
                            "pro_quarterly", 
                            priceProQuarterlyProvider(), 
                            activeQuarterly.orderId, 
                            activeQuarterly.purchaseTime
                        )
                    }
                    if (!activeQuarterly.isAcknowledged) {
                        coroutineScope.launch { acknowledgePurchase(activeQuarterly) }
                    }
                    onFinished?.invoke(true)
                } else if (activeMonthly != null) {
                    coroutineScope.launch {
                        repository.activatePremiumSubscription(
                            "pro_monthly", 
                            priceProMonthlyProvider(), 
                            activeMonthly.orderId, 
                            activeMonthly.purchaseTime
                        )
                    }
                    if (!activeMonthly.isAcknowledged) {
                        coroutineScope.launch { acknowledgePurchase(activeMonthly) }
                    }
                    onFinished?.invoke(true)
                } else {
                    // Check One-Time Lifetime Purchases (Premium Plan)
                    queryActiveInAppPurchases(onFinished)
                }
            } else {
                onFinished?.invoke(false)
            }
        }
    }

    private fun queryActiveInAppPurchases(onFinished: ((Boolean) -> Unit)? = null) {
        billingClient?.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        ) { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                val activePremium = purchases.firstOrNull { purchase ->
                    purchase.products.contains(PREMIUM_LIFETIME_PRODUCT_ID) &&
                            purchase.purchaseState == Purchase.PurchaseState.PURCHASED
                }

                if (activePremium != null) {
                    coroutineScope.launch {
                        repository.activatePremiumSubscription(
                            "premium", 
                            pricePremiumProvider(),
                            activePremium.orderId,
                            activePremium.purchaseTime
                        )
                    }
                    if (!activePremium.isAcknowledged) {
                        coroutineScope.launch { acknowledgePurchase(activePremium) }
                    }
                    onFinished?.invoke(true)
                } else {
                    // P0-3 FIX: Neither active subscription nor active in-app purchase was found.
                    // Revoke local Premium state and restore to gratuito.
                    coroutineScope.launch {
                        repository.restoreOrCancelSubscription()
                        Log.d("BillingManager", "No se detectaron compras activas en Google Play. Estado restablecido a Gratuito.")
                    }
                    onFinished?.invoke(false)
                }
            } else {
                onFinished?.invoke(false)
            }
        }
    }

    // Launch Google Play System Purchase Dialog Flow (P0-1 & P0-2 FIX)
    fun launchBillingFlow(
        activity: Activity,
        productId: String,
        productType: String,
        selectedBasePlanId: String? = null
    ) {
        if (!isClientConnected) {
            startConnection { launchBillingFlow(activity, productId, productType, selectedBasePlanId) }
            return
        }

        val productList = listOf(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(productId)
                .setProductType(productType)
                .build()
        )

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        billingClient?.queryProductDetailsAsync(params) { billingResult, queryProductDetailsResult ->
            val productDetailsList = queryProductDetailsResult.productDetailsList
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && !productDetailsList.isNullOrEmpty()) {
                val productDetails = productDetailsList.firstOrNull()
                if (productDetails != null) {
                    val productDetailsParamsList = if (productType == BillingClient.ProductType.SUBS) {
                        // P0-1 FIX: Retrieve genuine offerToken from subscriptionOfferDetails
                        val selectedOffer = if (!selectedBasePlanId.isNullOrBlank()) {
                            productDetails.subscriptionOfferDetails?.firstOrNull { it.basePlanId == selectedBasePlanId }
                                ?: productDetails.subscriptionOfferDetails?.firstOrNull()
                        } else {
                            productDetails.subscriptionOfferDetails?.firstOrNull()
                        }

                        val offerToken = selectedOffer?.offerToken
                        if (offerToken.isNullOrEmpty()) {
                            Log.e("BillingManager", "Error: No se encontró un offerToken válido para $productId en Google Play.")
                            if (BuildConfig.DEBUG) {
                                triggerDebugFallback(productId)
                            }
                            return@queryProductDetailsAsync
                        }

                        listOf(
                            BillingFlowParams.ProductDetailsParams.newBuilder()
                                .setProductDetails(productDetails)
                                .setOfferToken(offerToken)
                                .build()
                        )
                    } else {
                        listOf(
                            BillingFlowParams.ProductDetailsParams.newBuilder()
                                .setProductDetails(productDetails)
                                .build()
                        )
                    }

                    val billingFlowParams = BillingFlowParams.newBuilder()
                        .setProductDetailsParamsList(productDetailsParamsList)
                        .build()

                    billingClient?.launchBillingFlow(activity, billingFlowParams)
                }
            } else {
                Log.e("BillingManager", "Fallo al consultar detalles de producto de Google Play: ${billingResult.debugMessage}")
                // Safeguard activation for emulator/sandbox testing if product isn't configured in Play Store Console yet.
                if (BuildConfig.DEBUG) {
                    Log.w("BillingManager", "SANDBOX: Activando plan de prueba para desarrollo local.")
                    triggerDebugFallback(productId)
                } else {
                    Log.e("BillingManager", "PRODUCCIÓN: Producto no disponible en Play Console. Usuario permanece en plan gratuito.")
                }
            }
        }
    }

    private fun triggerDebugFallback(productId: String) {
        coroutineScope.launch {
            val (plan, price) = when (productId) {
                PRO_QUARTERLY_PRODUCT_ID -> Pair("pro_quarterly", priceProQuarterlyProvider())
                PRO_MONTHLY_PRODUCT_ID -> Pair("pro_monthly", priceProMonthlyProvider())
                else -> Pair("premium", pricePremiumProvider())
            }
            repository.activatePremiumSubscription(plan, price)
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            val isProQuarterly = purchase.products.contains(PRO_QUARTERLY_PRODUCT_ID)
            val isProMonthly = purchase.products.contains(PRO_MONTHLY_PRODUCT_ID)
            val isPremium = purchase.products.contains(PREMIUM_LIFETIME_PRODUCT_ID)

            if (isProQuarterly || isProMonthly || isPremium) {
                coroutineScope.launch {
                    val (plan, price) = when {
                        isPremium -> Pair("premium", pricePremiumProvider())
                        isProQuarterly -> Pair("pro_quarterly", priceProQuarterlyProvider())
                        else -> Pair("pro_monthly", priceProMonthlyProvider())
                    }
                    repository.activatePremiumSubscription(
                        plan, 
                        price, 
                        purchase.orderId, 
                        purchase.purchaseTime
                    )
                }
            }

            if (!purchase.isAcknowledged) {
                coroutineScope.launch { acknowledgePurchase(purchase) }
            }
        }
    }

    private suspend fun acknowledgePurchase(purchase: Purchase) {
        val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        billingClient?.let { client ->
            val billingResult = client.acknowledgePurchase(acknowledgePurchaseParams)
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                Log.d("BillingManager", "Compra de Google Play confirmada y acreditada sin riesgo de reembolso automático.")
            } else {
                Log.e("BillingManager", "Acreditación fallida: ${billingResult.debugMessage}")
            }
        }
    }
}
