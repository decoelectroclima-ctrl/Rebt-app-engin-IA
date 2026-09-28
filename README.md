<div align="center">
<img width="1200" height="475" alt="GHBanner" src="https://ai.google.dev/static/site-assets/images/share-ais-513315318.png" />
</div>

# EnginIA REBT — App Offline de Estudio y Preparación Oficial

**Versión actual:** 21.0 (versionCode 21)  
**ID de aplicación:** `com.aistudio.enginia.pwtvzc`

Aplicación nativa Android (Kotlin + Jetpack Compose + Room) 100 % offline para la preparación exhaustiva de exámenes oficiales de Instalador Autorizado en Baja Tensión (REBT 2026).

---

## 🏛️ Sistema de Exámenes y Simuladores

1. **Simulacro Oficial REBT (40 Preguntas · 90 minutos)**:
   - Formato real de examen oficial (umbral de aprobado: 75 %).
   - Selección estratificada y reproducible: ~25 % Articulado (10 preguntas) y ~75 % Instrucciones Técnicas Complementarias (30 preguntas).
   - Ámbito de **Categoría Básica (IBTB)**: exclusión automática de ITCs exclusivas de especialista (6, 7, 38, 51).
   - Sin preguntas duplicadas en el examen y priorización de preguntas no vistas en el último simulacro de la sesión.
   - Pantalla de resultados con nota sobre 10, calificación APTO / NO APTO y desglose normativo por bloque (Articulado / ITCs).

2. **Test por ITC (Bloques de 20 Preguntas · 60 minutos)**:
   - Selector completo para **Articulado REBT (Art. 1-29)** y cada una de las **52 ITCs reglamentarias**.
   - Contador de preguntas disponibles por ITC y porcentaje histórico de aciertos del usuario.
   - Distintivo visual "Solo especialista" para ITCs reservadas a la Categoría Especialista (IBTE).

3. **Repaso de Errores y Repetición Espaciada**:
   - Seguimiento local de fallos en base de datos Room.
   - Banco de preguntas 100 % íntegro (sin preguntas sintéticas o ficticias).

4. **Laboratorio Electrotécnico y Apuntes**:
   - Calculadoras normativas de sección por caída de tensión e intensidad admisible (Iz).
   - Previsión de cargas en edificios (ITC-BT-10), tubos (ITC-BT-21) y puesta a tierra (ITC-BT-18).
   - Sistema de Posits y chuletas técnicas de examen.

---

## 📊 Banco de Preguntas y Validación

- **Total de preguntas en el banco:** 228 preguntas normativas completas con justificación técnica y referencia reglamentaria canónica.
- **Validador de Integridad (`QuestionValidator`)**:
  - Exactamente 4 opciones por pregunta.
  - Índice de respuesta `a` en rango 0..3.
  - Sin opciones vacías ni duplicadas.
  - Enunciados únicos en todo el catálogo.
  - Referencias parseables (`itcNumber()` o `isArticulado()`).
  - Verificado en build con tests unitarios JUnit/Robolectric y validado en `DEBUG` durante el arranque.

---

## 🛡️ Seguridad y Compilación

- Proguard/R8 activado (`isMinifyEnabled = true`)
- Backup de datos restringido (`android:allowBackup="false"`)
- Google Play Billing Library 8.0.0 (protegido con `BuildConfig.DEBUG` en modo local)
- 100 % funcional sin conexión a Internet

```bash
# Compilar y ejecutar tests de validación unitaria
./gradlew testDebugUnitTest

# Generar APK de depuración
./gradlew assembleDebug

# Generar bundle de producción optimizado
./gradlew bundleRelease
```
