package com.example.ui.screens.subscription

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.BuildConfig
import com.example.MainViewModel
import com.example.ui.FeedbackManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Helper to safely resolve Activity through ContextWrapper layers (P0-4 fix)
fun Context.findActivity(): Activity? {
    var ctx = this
    while (ctx is ContextWrapper) {
        if (ctx is Activity) return ctx
        ctx = ctx.baseContext
    }
    return null
}

data class PlanFeature(
    val title: String,
    val freeText: String,
    val proText: String,
    val lifetimeText: String,
    val isKeyHighlight: Boolean = false
)

data class PlanOption(
    val id: String,
    val name: String,
    val badge: String?,
    val badgeColor: Color,
    val price: String,
    val pricePeriod: String,
    val savingsText: String?,
    val description: String,
    val isPopular: Boolean = false,
    val planKey: String, // "pro" or "premium"
    val numericPrice: Double
)

data class FaqItem(
    val question: String,
    val answer: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val subscription by viewModel.subscriptionFlow.collectAsState()
    val playPrices by viewModel.formattedPrices.collectAsState()
    val isPremium = subscription?.isActive == true
    val activePlanKey = subscription?.plan ?: "gratuito"

    // Default selected plan in UI
    var selectedPlanId by remember { mutableStateOf("plan_trimestral") }

    BackHandler {
        viewModel.activeTab = "settings"
    }

    // Dynamic prices from Google Play with reliable local defaults
    val monthlyPriceStr = playPrices[viewModel.billingManager.PRO_MONTHLY_PRODUCT_ID] ?: "14,99 €"
    val quarterlyPriceStr = playPrices[viewModel.billingManager.PRO_QUARTERLY_PRODUCT_ID] ?: "29,99 €"
    val lifetimePriceStr = playPrices[viewModel.billingManager.PREMIUM_LIFETIME_PRODUCT_ID] ?: "49,99 €"

    val plans = listOf(
        PlanOption(
            id = "plan_mensual",
            name = "Aspirante Mensual",
            badge = "FLEXIBLE",
            badgeColor = Color(0xFF58A6FF),
            price = monthlyPriceStr,
            pricePeriod = "/ mes",
            savingsText = "Cancela cuando quieras",
            description = "Ideal si tu examen es inminente y necesitas simulacros ilimitados durante las próximas 4 semanas.",
            isPopular = false,
            planKey = "pro",
            numericPrice = 14.99
        ),
        PlanOption(
            id = "plan_trimestral",
            name = "Plan Convocatoria",
            badge = "MÁS POPULAR PARA EL EXAMEN",
            badgeColor = Color(0xFFF59E0B),
            price = quarterlyPriceStr,
            pricePeriod = "/ 3 meses",
            savingsText = "Ahorras un 33 % (aprox. 9,99 €/mes)",
            description = "El plazo recomendado para asimilar el temario, dominar las ITCs y consolidar el aprobado.",
            isPopular = true,
            planKey = "pro",
            numericPrice = 29.99
        ),
        PlanOption(
            id = "plan_vitalicio",
            name = "Instalador Pro Vitalicio",
            badge = "PAGO ÚNICO · SIN CUOTAS",
            badgeColor = Color(0xFF10B981),
            price = lifetimePriceStr,
            pricePeriod = "pago único",
            savingsText = "Sin renovaciones jamás · Para toda tu carrera",
            description = "Para instaladores y técnicos electricistas. Acceso permanente sin cuotas mensuales para tus consultas y cálculos en obra.",
            isPopular = false,
            planKey = "premium",
            numericPrice = 49.99
        )
    )

    // Accurate features matrix matching the actual app implementation (P0-5 fix)
    val comparisonFeatures = listOf(
        PlanFeature(
            title = "Simulacros Oficiales REBT (40 preg. / 90 min)",
            freeText = "1 simulacro demo",
            proText = "Ilimitados",
            lifetimeText = "Ilimitados",
            isKeyHighlight = true
        ),
        PlanFeature(
            title = "Simulacros Cortos de Práctica (20 preg.)",
            freeText = "Ilimitados",
            proText = "Ilimitados",
            lifetimeText = "Ilimitados",
            isKeyHighlight = false
        ),
        PlanFeature(
            title = "Test por ITC reglamentarias",
            freeText = "Articulado e ITCs 01 a 05",
            proText = "Catálogo completo",
            lifetimeText = "Catálogo completo",
            isKeyHighlight = true
        ),
        PlanFeature(
            title = "Reparto estratificado oficial (25% Art. / 75% ITC)",
            freeText = "En simulacro demo",
            proText = "Siempre activo",
            lifetimeText = "Siempre activo",
            isKeyHighlight = false
        ),
        PlanFeature(
            title = "Repaso inteligente de fallos (test interactivo)",
            freeText = "Solo lectura de fallos",
            proText = "Test interactivo activo",
            lifetimeText = "Test interactivo activo",
            isKeyHighlight = true
        ),
        PlanFeature(
            title = "Laboratorio Electrotécnico",
            freeText = "Conductores y tubos",
            proText = "Todas (cargas, tierras, Iz)",
            lifetimeText = "Todas las calculadoras",
            isKeyHighlight = false
        ),
        PlanFeature(
            title = "Chuletas Oficiales en Posits",
            freeText = "10 chuletas básicas",
            proText = "19+ chuletas en local",
            lifetimeText = "19+ chuletas en local",
            isKeyHighlight = false
        ),
        PlanFeature(
            title = "Analítica de Probabilidad de APTO",
            freeText = "Básica",
            proText = "Diagnóstico detallado",
            lifetimeText = "Diagnóstico detallado",
            isKeyHighlight = true
        ),
        PlanFeature(
            title = "Funcionamiento 100 % Offline en obra",
            freeText = "Sí",
            proText = "Sí",
            lifetimeText = "Sí",
            isKeyHighlight = false
        ),
        PlanFeature(
            title = "Sin cuotas de suscripción periódicas",
            freeText = "Gratis",
            proText = "Cuota periódica",
            lifetimeText = "PAGO ÚNICO DEFINITIVO",
            isKeyHighlight = true
        )
    )

    val faqs = listOf(
        FaqItem(
            question = "¿Qué plan me conviene más para prepararme el examen?",
            answer = "Si tienes fecha de examen en los próximos 2 o 3 meses, el Plan Convocatoria (Trimestral) es el más eficiente: te permite hacer simulacros oficiales de 40 preguntas ilimitados y testear todas las ITCs por solo 9,99 €/mes equivalente. Si trabajas habitualmente en obra o proyectos y buscas una herramienta permanente de consulta, el Plan Vitalicio te da acceso de por vida sin cuotas mensuales."
        ),
        FaqItem(
            question = "¿Cómo funciona la garantía y la cancelación de suscripción?",
            answer = "Las suscripciones periódicas se gestionan íntegramente a través de Google Play Store. Puedes cancelar en cualquier instante con 1 clic desde tu cuenta de Google Play y seguirás disfrutando del acceso Pro hasta el último día del período contratado, sin renovaciones sorpresa."
        ),
        FaqItem(
            question = "¿Funciona la app y el contenido si no tengo internet?",
            answer = "¡Sí, al 100 %! Toda la base de datos de preguntas, temarios, calculadoras de laboratorio y apuntes técnicos residen de forma local en tu dispositivo (base de datos Room nativa). Puedes hacer exámenes en el tren, en sótanos o en obras sin internet."
        ),
        FaqItem(
            question = "¿Qué condiciones tiene el Plan Vitalicio (Licencia Definitiva)?",
            answer = "El Plan Vitalicio es un pago único que te otorga acceso permanente a todas las funcionalidades Pro de EnginIA REBT para siempre, sin cuotas periódicas, incluyendo las actualizaciones normativas y mejoras técnicas que se publiquen en la aplicación."
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.WorkspacePremium,
                            contentDescription = null,
                            tint = Color(0xFFF59E0B),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Planes y Suscripción",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Preparación oficial REBT 2026 sin límites",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            FeedbackManager.playClick(context)
                            viewModel.activeTab = "settings"
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver a Ajustes"
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            FeedbackManager.playClick(context)
                            Toast.makeText(context, "Consultando compras en Google Play...", Toast.LENGTH_SHORT).show()
                            viewModel.restoreSubscription { hasActive ->
                                val msg = if (hasActive) {
                                    "¡Suscripción activa recuperada y verificada!"
                                } else {
                                    "No se encontraron compras activas asociadas a esta cuenta."
                                }
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                            }
                        }
                    ) {
                        Text(
                            text = "Restaurar",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 48.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Current Status Banner (if user already has active plan)
            if (isPremium) {
                item {
                    ActiveSubscriptionStatusCard(
                        subscriptionPlan = activePlanKey,
                        price = subscription?.price ?: 0.0,
                        purchaseTime = subscription?.purchaseTime ?: 0L,
                        transactionId = subscription?.transactionId,
                        onManageGooglePlay = {
                            FeedbackManager.playClick(context)
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/account/subscriptions")
                            )
                            context.startActivity(intent)
                        },
                        onCancelDev = {
                            FeedbackManager.playClick(context)
                            viewModel.cancelSubscriptionDev()
                            Toast.makeText(context, "Suscripción dev restablecida a Gratuito", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }

            // 2. Hero Proposition Header
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color(0xFFFFFFFF)
                    ),
                    border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFF59E0B).copy(alpha = 0.12f),
                                        Color.Transparent
                                    )
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = Color(0xFFF59E0B).copy(alpha = 0.18f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Bolt,
                                        contentDescription = null,
                                        tint = Color(0xFFF59E0B),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "REBT 2026 OFICIAL",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 11.sp,
                                        color = Color(0xFFF59E0B)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Asegura tu Aprobado Oficial en la Primera Convocatoria",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 26.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Desbloquea simulacros oficiales de 40 preguntas ilimitados, el catálogo completo de ITCs y el laboratorio técnico para trabajar en obra con total seguridad.",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Trust Badges Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                TrustBadgeItem(icon = Icons.Default.CloudOff, label = "100% Offline")
                                TrustBadgeItem(icon = Icons.Default.Block, label = "Sin Publicidad")
                                TrustBadgeItem(icon = Icons.Default.VerifiedUser, label = "Google Play")
                            }
                        }
                    }
                }
            }

            // 3. Plan Selection Cards
            item {
                Text(
                    text = "Elige tu Modalidad de Acceso",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
            }

            items(plans) { plan ->
                val isSelected = selectedPlanId == plan.id
                PlanCardItem(
                    plan = plan,
                    isSelected = isSelected,
                    isDarkTheme = viewModel.isDarkTheme,
                    onClick = {
                        FeedbackManager.playClick(context)
                        selectedPlanId = plan.id
                    }
                )
            }

            // 4. CTA Action Button for Selected Plan (P0-4 FIX: ContextWrapper + BuildConfig.DEBUG safeguard)
            item {
                val currentSelectedPlan = plans.first { it.id == selectedPlanId }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            FeedbackManager.playClick(context)
                            val act = context.findActivity()
                            if (act != null) {
                                viewModel.purchaseSubscription(act, currentSelectedPlan.id, currentSelectedPlan.planKey)
                            } else if (BuildConfig.DEBUG) {
                                viewModel.activatePlanDirect(currentSelectedPlan.planKey, currentSelectedPlan.numericPrice)
                            } else {
                                Toast.makeText(context, "No se pudo iniciar el servicio de facturación de Google Play.", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("subscribe_button_${currentSelectedPlan.id}"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentSelectedPlan.isPopular) Color(0xFFE67E22) else MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = if (currentSelectedPlan.planKey == "premium") Icons.Default.Star else Icons.Default.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Comenzar con ${currentSelectedPlan.name} (${currentSelectedPlan.price})",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Text(
                        text = "Pago 100% seguro a través de Google Play Store. Cancelable en cualquier momento.",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    // Dev / Sandbox fast activation helper (Strictly DEBUG only)
                    if (BuildConfig.DEBUG) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "🛠️ MODO DESARROLLADOR / SANDBOX",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Simula la compra sin pasar por Play Console para probar la app localmente:",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            FeedbackManager.playClick(context)
                                            viewModel.activatePlanDirect("pro_monthly", 14.99)
                                            Toast.makeText(context, "Plan PRO Mensual activado en BD local", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Pro Mensual Dev", fontSize = 11.sp)
                                    }
                                    OutlinedButton(
                                        onClick = {
                                            FeedbackManager.playClick(context)
                                            viewModel.activatePlanDirect("pro_quarterly", 29.99)
                                            Toast.makeText(context, "Plan Trimestral activado en BD local", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Trimestral Dev", fontSize = 11.sp)
                                    }
                                    OutlinedButton(
                                        onClick = {
                                            FeedbackManager.playClick(context)
                                            viewModel.activatePlanDirect("premium", 49.99)
                                            Toast.makeText(context, "Plan Vitalicio activado en BD local", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Vitalicio Dev", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 5. Features Comparison Matrix
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Comparativa de Niveles",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Compara las características incluidas en cada modalidad:",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (viewModel.isDarkTheme) Color(0xFF161B22) else Color.White
                    ),
                    border = BorderStroke(1.dp, if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        // Header row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Característica",
                                modifier = Modifier.weight(1.5f),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Gratis",
                                modifier = Modifier.weight(1f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Pro",
                                modifier = Modifier.weight(1.1f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = Color(0xFFF59E0B)
                            )
                            Text(
                                text = "Vitalicio",
                                modifier = Modifier.weight(1.2f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = Color(0xFF10B981)
                            )
                        }

                        HorizontalDivider(color = if (viewModel.isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8))

                        comparisonFeatures.forEach { feature ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1.5f)) {
                                    Text(
                                        text = feature.title,
                                        fontSize = 12.sp,
                                        fontWeight = if (feature.isKeyHighlight) FontWeight.Bold else FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }

                                Text(
                                    text = feature.freeText,
                                    modifier = Modifier.weight(1f),
                                    fontSize = 11.sp,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Text(
                                    text = feature.proText,
                                    modifier = Modifier.weight(1.1f),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                    color = Color(0xFFF59E0B)
                                )

                                Text(
                                    text = feature.lifetimeText,
                                    modifier = Modifier.weight(1.2f),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    color = Color(0xFF10B981)
                                )
                            }
                            HorizontalDivider(
                                color = if (viewModel.isDarkTheme) Color(0xFF21262D) else Color(0xFFF0F2F5),
                                thickness = 0.5.dp
                            )
                        }
                    }
                }
            }

            // 6. Frequently Asked Questions (FAQ)
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Preguntas Frecuentes",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            items(faqs) { faq ->
                FaqAccordionItem(faq = faq, isDarkTheme = viewModel.isDarkTheme)
            }
        }
    }
}

@Composable
private fun ActiveSubscriptionStatusCard(
    subscriptionPlan: String,
    price: Double,
    purchaseTime: Long,
    transactionId: String?,
    onManageGooglePlay: () -> Unit,
    onCancelDev: () -> Unit
) {
    val dateString = if (purchaseTime > 0L) {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(purchaseTime))
    } else {
        "Reciente"
    }

    val planTitle = when (subscriptionPlan) {
        "premium" -> "Plan Instalador Pro Vitalicio"
        "pro_quarterly" -> "Plan Convocatoria (Trimestral)"
        "pro_monthly" -> "Plan Aspirante Pro (Mensual)"
        else -> "Plan Pro Activo"
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF10B981).copy(alpha = 0.12f)
        ),
        border = BorderStroke(1.5.dp, Color(0xFF10B981))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF10B981)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "¡Estás suscrito a $planTitle!",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Activo desde: $dateString · ${if (transactionId != null) "Ref: ${transactionId.take(16)}..." else "Google Play"}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onManageGooglePlay,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Gestionar en Play Store", fontSize = 12.sp)
                }

                if (BuildConfig.DEBUG) {
                    TextButton(
                        onClick = onCancelDev,
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Revertir Dev", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun PlanCardItem(
    plan: PlanOption,
    isSelected: Boolean,
    isDarkTheme: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                if (isDarkTheme) Color(0xFF1C2128) else Color(0xFFF6F8FA)
            } else {
                if (isDarkTheme) Color(0xFF161B22) else Color.White
            }
        ),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) plan.badgeColor else if (isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag(plan.id)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    if (plan.badge != null) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = plan.badgeColor.copy(alpha = 0.16f)
                        ) {
                            Text(
                                text = plan.badge,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                color = plan.badgeColor,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    Text(
                        text = plan.name,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Radio-like selection indicator
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .border(
                            2.dp,
                            if (isSelected) plan.badgeColor else Color.Gray.copy(alpha = 0.5f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(plan.badgeColor)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Pricing Row
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = plan.price,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = plan.pricePeriod,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }

            if (plan.savingsText != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = plan.savingsText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = plan.badgeColor
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = plan.description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun TrustBadgeItem(icon: ImageVector, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun FaqAccordionItem(faq: FaqItem, isDarkTheme: Boolean) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) Color(0xFF161B22) else Color.White
        ),
        border = BorderStroke(1.dp, if (isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = faq.question,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    HorizontalDivider(
                        color = if (isDarkTheme) Color(0xFF30363D) else Color(0xFFE1E4E8),
                        thickness = 0.5.dp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = faq.answer,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 17.sp
                    )
                }
            }
        }
    }
}
