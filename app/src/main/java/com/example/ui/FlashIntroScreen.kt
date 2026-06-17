package com.example.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun FlashIntroScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    var progress by remember { mutableStateOf(0.0f) }
    var currentStepText by remember { mutableStateOf("Iniciando terminal REBT...") }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 3500, easing = LinearEasing),
        label = "progress"
    )

    // Cosmic theme styling colors
    val neonBlue = Color(0xFF58A6FF)
    val neonGold = Color(0xFFF1C40F)
    val darkBg = Color(0xFF07090C)

    // Trigger startup chime sound & vibrating pulse on launching the intro
    LaunchedEffect(Unit) {
        FeedbackManager.playIntroChime(context)

        // Steps sequence mimicking vintage electrical multimeter calibration & diagnostics
        delay(300)
        progress = 0.15f
        currentStepText = "Calibrando multímetro..."
        delay(600)
        progress = 0.35f
        currentStepText = "Verificando derivación de tierra..."
        delay(700)
        progress = 0.60f
        currentStepText = "Cargando ITC-BT-52 (Vehículos eléctricos)..."
        delay(850)
        progress = 0.85f
        currentStepText = "Conectado al servidor de Industria..."
        delay(600)
        progress = 1.0f
        currentStepText = "Sistemas REBT calibrados a 230V [OK]"
        
        // Auto-complete intro screen after 4.5 seconds
        delay(700)
        viewModel.showFlashIntro = false
    }

    // Background custom electric scanline grid animation
    val infiniteTransition = rememberInfiniteTransition(label = "scanline")
    val scanY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "y"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBg)
            .drawWithContent {
                drawContent()
                // Glowing vector scanlines effect representing old mechanical CRT screen
                val h = size.height
                val w = size.width
                val lineY = scanY * h
                drawLine(
                    color = neonBlue.copy(alpha = 0.12f),
                    start = Offset(0f, lineY),
                    end = Offset(w, lineY),
                    strokeWidth = 3.dp.toPx()
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Electrical symbol pulsing halo representation
            val scale by infiniteTransition.animateFloat(
                initialValue = 0.9f,
                targetValue = 1.15f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1200, easing = EaseInOutSine),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "glowingScale"
            )

            Box(
                modifier = Modifier
                    .size(110.dp)
                    .background(neonBlue.copy(alpha = 0.08f), CircleShape)
                    .border(BorderStroke(2.dp * scale, neonBlue.copy(alpha = 0.3f)), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = "Rayo",
                    tint = neonGold,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Main Title
            Text(
                text = "ENGIN-IA REBT",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                letterSpacing = 4.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = "PREPARACIÓN HIGH-TECH PARA EXAMEN OFICIAL",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = neonBlue,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Dynamic Terminal Shell Frame with Diagnostic details
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F141C)),
                border = BorderStroke(1.dp, Color(0xFF21262D))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color.Red)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color.Yellow)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color.Green)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "terminal_rebt_diag.sh",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            color = Color.Gray
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFF21262D))

                    // Simulated Console prints
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        DiagnosticConsoleRow(label = "SISTEMA OPERATIVO", value = "Android SDK " + android.os.Build.VERSION.SDK_INT, status = "OK", color = neonBlue)
                        DiagnosticConsoleRow(label = "VOLTAJE REBT", value = "230 V AC (Monofásica)", status = "OK", color = Color.Green)
                        DiagnosticConsoleRow(label = "RESISTENCIA TIERRA", value = "12.4 Ω", status = "OK", color = Color.Green)
                        DiagnosticConsoleRow(label = "DIFERENCIAL COGNITIVO", value = "Aislado 0.03 A", status = "ONLINE", color = neonGold)
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Interactive diagnostic status bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = currentStepText,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = neonBlue,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${(animatedProgress * 100).toInt()}%",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = neonGold,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    LinearProgressIndicator(
                        progress = { animatedProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = neonBlue,
                        trackColor = Color(0xFF21262D)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Bypass / Entrar manual trigger button
            Button(
                onClick = {
                    FeedbackManager.playClick(context)
                    viewModel.showFlashIntro = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = neonGold,
                    contentColor = Color.Black
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ElectricalServices, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ENTRAR CON LICENCIA PRO",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "jj.terapias@gmail.com - Licencia Activa",
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Monospace,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DiagnosticConsoleRow(label: String, value: String, status: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Text(
                text = "• $label: ",
                fontSize = 10.sp,
                color = Color.LightGray,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = value,
                fontSize = 10.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
        }
        Text(
            text = "[$status]",
            fontSize = 10.sp,
            color = color,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.End
        )
    }
}
