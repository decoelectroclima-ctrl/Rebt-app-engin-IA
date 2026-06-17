package com.example.ui

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.HapticFeedbackConstants
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object FeedbackManager {
    private var toneGenerator: ToneGenerator? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        try {
            // Volume set to 80%
            toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 80)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getVibrator(context: Context): Vibrator? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }
        } catch (e: Exception) {
            null
        }
    }

    fun playClick(context: Context) {
        scope.launch {
            try {
                // Play subtle beep tone
                toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 35)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // Touch feedback
        vibrate(context, 20)
    }

    fun playCorrect(context: Context) {
        scope.launch {
            try {
                // Majestic dual-ascending tone
                toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 80)
                delay(100)
                toneGenerator?.startTone(ToneGenerator.TONE_PROP_PROMPT, 120)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // Subtle friendly pulse
        vibrate(context, 120)
    }

    fun playIncorrect(context: Context) {
        scope.launch {
            try {
                // Low-pitched error double buzz
                toneGenerator?.startTone(ToneGenerator.TONE_SUP_ERROR, 280)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // Heavy buzzing pulse
        vibrate(context, 350)
    }

    fun playTripPower(context: Context) {
        scope.launch {
            try {
                // Simulated physical breaker snap: short sound clicks
                toneGenerator?.startTone(ToneGenerator.TONE_SUP_DIAL, 60)
                delay(70)
                toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP2, 100)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // Strong double physical click vibration
        vibratePattern(context, longArrayOf(0, 40, 60, 100))
    }

    fun playIntroChime(context: Context) {
        scope.launch {
            try {
                // High-fidelity ascending chord sequence
                toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 80)
                delay(120)
                toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP2, 100)
                delay(150)
                toneGenerator?.startTone(ToneGenerator.TONE_PROP_PROMPT, 130)
                delay(200)
                toneGenerator?.startTone(ToneGenerator.TONE_CDMA_PIP, 250)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // Sequence of rhythmic short pulses
        vibratePattern(context, longArrayOf(0, 40, 100, 50, 150, 80, 200, 120))
    }

    private fun vibrate(context: Context, durationMs: Long) {
        try {
            val vibrator = getVibrator(context) ?: return
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(durationMs)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun vibratePattern(context: Context, pattern: LongArray) {
        try {
            val vibrator = getVibrator(context) ?: return
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(pattern, -1)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
