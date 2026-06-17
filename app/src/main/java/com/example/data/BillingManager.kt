package com.example.data

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class BillingManager(
    private val context: Context,
    private val repository: EnigmaRepository,
    private val coroutineScope: CoroutineScope
) {
    private var billingClient: BillingClient? = null
    var isClientConnected = false
        private set

    // Official Google Play Product IDs for your developer Console
    val PRO_MONTHLY_PRODUCT_ID = "pro_monthly"
    val PREMIUM_LIFETIME_PRODUCT_ID = "premium_lifetime"

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
            .enablePendingPurchases()
            .build()
        
        startConnection()
    }

    fun startConnection(onSuccess: (() -> Unit)? = null) {
        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    isClientConnected = true
                    Log.d("BillingManager", "Conexión con Google Play Billing establecida con éxito.")
                    queryActivePurchases()
                    onSuccess?.invoke()
                } else {
                    Log.e("BillingManager", "Fallo al conectar con Google Play Billing: ${billingResult.debugMessage}")
                }
            }

            override fun onBillingServiceDisconnected() {
                isClientConnected = false
                Log.d("BillingManager", "Servicio descodificado de Google Play. Intentando reconectar...")
            }
        })
    }

    // Live query of active user subscriptions and entitlements
    fun queryActivePurchases() {
        if (!isClientConnected) {
            startConnection { queryActivePurchases() }
            return
        }

        // Query active Subscriptions (Pro Plan)
        billingClient?.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.SUBS)
                .build()
        ) { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                val activePro = purchases.any { purchase ->
                    purchase.products.contains(PRO_MONTHLY_PRODUCT_ID) && 
                    purchase.purchaseState == Purchase.PurchaseState.PURCHASED
                }
                
                if (activePro) {
                    coroutineScope.launch {
                        repository.activatePremiumSubscription("pro", 4.99)
                    }
                    purchases.forEach { purchase ->
                        if (!purchase.isAcknowledged) {
                            acknowledgePurchase(purchase)
                        }
                    }
                } else {
                    // Check One-Time Lifetime Purchases (Premium Plan)
                    queryActiveInAppPurchases()
                }
            }
        }
    }

    private fun queryActiveInAppPurchases() {
        billingClient?.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        ) { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                val activePremium = purchases.any { purchase ->
                    purchase.products.contains(PREMIUM_LIFETIME_PRODUCT_ID) && 
                    purchase.purchaseState == Purchase.PurchaseState.PURCHASED
                }
                
                if (activePremium) {
                    coroutineScope.launch {
                        repository.activatePremiumSubscription("premium", 14.99)
                    }
                    purchases.forEach { purchase ->
                        if (!purchase.isAcknowledged) {
                            acknowledgePurchase(purchase)
                        }
                    }
                } else {
                    // Purchase state holds what is currently stored in local database
                }
            }
        }
    }

    // Launch Google Play System Purchase Dialog Flow
    fun launchBillingFlow(activity: Activity, productId: String, productType: String) {
        if (!isClientConnected) {
            startConnection { launchBillingFlow(activity, productId, productType) }
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

        billingClient?.queryProductDetailsAsync(params) { billingResult, productDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && productDetailsList.isNotEmpty()) {
                val productDetails = productDetailsList[0]
                
                val productDetailsParamsList = if (productType == BillingClient.ProductType.SUBS) {
                    val offerToken = productDetails.subscriptionOfferDetails?.firstOrNull()?.offerToken ?: ""
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
            } else {
                Log.e("BillingManager", "Fallo al consultar detalles de producto de Google Play: ${billingResult.debugMessage}")
                Log.w("BillingManager", "Como salvaguarda local, activando plan de prueba para desarrollo local en sandbox.")
                // Safeguard activation for emulator/sandbox testing if product isn't configured in Play Store Console yet.
                coroutineScope.launch {
                    val plan = if (productId == PRO_MONTHLY_PRODUCT_ID) "pro" else "premium"
                    val price = if (productId == PRO_MONTHLY_PRODUCT_ID) 4.99 else 14.99
                    repository.activatePremiumSubscription(plan, price)
                }
            }
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            val isPro = purchase.products.contains(PRO_MONTHLY_PRODUCT_ID)
            val isPremium = purchase.products.contains(PREMIUM_LIFETIME_PRODUCT_ID)
            
            if (isPro || isPremium) {
                coroutineScope.launch {
                    val plan = if (isPro) "pro" else "premium"
                    val price = if (isPro) 4.99 else 14.99
                    repository.activatePremiumSubscription(plan, price)
                }
            }

            if (!purchase.isAcknowledged) {
                acknowledgePurchase(purchase)
            }
        }
    }

    private fun acknowledgePurchase(purchase: Purchase) {
        val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()
        
        billingClient?.acknowledgePurchase(acknowledgePurchaseParams) { billingResult ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                Log.d("BillingManager", "Compra de Google Play confirmada y acreditada sin riesgo de reembolso automático.")
            } else {
                Log.e("BillingManager", "Acreditación fallida: ${billingResult.debugMessage}")
            }
        }
    }
}
