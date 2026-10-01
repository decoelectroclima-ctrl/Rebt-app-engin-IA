# Changelog

## v26.0 - 01 Octubre 2026

### Consolidación y Resolución de las 5 Tareas REBT
- **Tarea 1 (Punto de Restauración & Checkpoint)**: Formalizado y etiquetado el checkpoint `checkpoint-v25` y la versión `v26.0` en el control de versiones.
- **Tarea 2 (Sanitización y Deduplicación CSV)**: Depuración de `questions.csv` reduciéndolo a 7 preguntas normativas puras, sin duplicados con `Content.kt` y con erradicación de fugas del indicador "(correcta)".
- **Tarea 3 (Unificación del Banco y Eliminación de Shadowing)**: Corregido el shadowing en `MainViewModel` (`startItcPractice`, `startArticuladoPractice`, `startOfficialSimulation`); todas las modalidades consumen de forma coherente el banco integrado (`Content.kt` + CSV fusionado). Eliminado el script temporal `fix_vm.py`.
- **Tarea 4 (Barajado Aleatorio Seguro en Interfaz)**: Implementación de barajado dinámico de opciones en `ActiveExamView` (`remember(currentQuestion, module.id)`) con recálculo transparente del índice de respuesta correcta y excepción inteligente para preguntas con dependencia de orden textual (`hasOrderDependentOptions()`).
- **Tarea 5 (Cobertura de las 52 ITCs)**: Rellenadas todas las ITCs del reglamento (52 de 52 con cobertura activa en `Content.kt`).
- **Configuración de Release**: `versionCode = 26`, `versionName = "26.0"`, `isMinifyEnabled = true`, `allowBackup = false`, y `applicationId = "com.aistudio.enginia.pwtvzc"`.

## v24.0 - 28 Septiembre 2026

### Auditoría y Blindaje de Facturación y Límites de Suscripción (Corrección P0 Completa)
- **Corrección P0-1 (Billing Library 8.0 / OfferToken)**: Extracción rigurosa del `offerToken` genuino desde `subscriptionOfferDetails` para suscripciones (`SUBS`), impidiendo compras fallidas por tokens vacíos.
- **Corrección P0-2 (Planes Base Mensual y Trimestral)**: Diferenciación completa en facturación y catálogo de productos Play (`pro_monthly` con base plan `monthly` y `pro_quarterly` con base plan `quarterly`), permitiendo cobros exactos según el período seleccionado.
- **Corrección P0-3 (Revocación Automática de Acceso Caducado)**: `queryActivePurchases()` ahora revoca y restablece automáticamente el estado local a Gratuito cuando Google Play confirma la ausencia de compras activas (por cancelación, impago, fin de período o reembolso).
- **Corrección P0-4 (Blindaje de Concesión Directa)**: `activatePlanDirect` y `cancelSubscriptionDev` quedan estrictamente encapsulados con guarda `BuildConfig.DEBUG`, y el Context se resuelve recursivamente vía `ContextWrapper.findActivity()`, imposibilitando accesos Premium fortuitos en compilaciones de producción.
- **Corrección P0-5 (Cumplimiento Estricto de Límites Prometidos)**:
  - **Simulacros Oficiales REBT (40 preg. / 90 min)**: 1 simulacro demo gratuito; a partir del segundo se exige Plan Pro.
  - **Test por ITC**: Articulado e ITCs 01 a 05 disponibles gratuitamente; ITCs 06 a 52 bloqueadas con indicador visual `PRO` y diálogo explicativo.
  - **Laboratorio de Cálculo**: Conductores (ITC-14/19) y Tubos (ITC-21) 100% gratuitos; Previsión de Cargas en Edificios (ITC-10) y Tierras (ITC-18) reservadas para el Plan Pro con tarjetas de previsualización técnica.
  - **Chuletas y Posits**: Límite de 10 chuletas básicas en plan gratuito; tarjeta de desbloqueo para las 19+ chuletas restantes y notas ilimitadas.
  - **Repaso Inteligente de Fallos**: Lectura y consulta de explicaciones abierta para todos; modo test interactivo con repetición espaciada reservado para Plan Pro.
  - **Analítica de Aprendizaje**: Índice de preparación global gratuito; diagnóstico detallado por módulos temáticos reservado para Pro.
- **Corrección P0-6 / Errores Menores**:
  - Consulta y visualización dinámica de precios reales formateados desde Google Play (`formattedPrice`).
  - Precios por defecto y precios guardados corregidos con exactitud en Room: Mensual (14,99 €), Trimestral (29,99 €) y Vitalicio (49,99 €).
  - Clarificación del texto comercial del paywall para reflejar fielmente la base de datos y la arquitectura 100% offline sin promesas de sincronización en la nube innecesarias.

---

## v22.0 - 28 Septiembre 2026

### Modelo Coherente de Planes y Suscripción
- **Nueva Pantalla de Planes (`SubscriptionScreen`)**: Presentación exhaustiva y pedagógica de modalidades de suscripción adaptadas a los perfiles de aspirante a instalador y profesional en ejercicio.
  - **Plan Aspirante Mensual (14,99 €/mes)**: Máxima flexibilidad para aspirantes con examen inminente.
  - **Plan Convocatoria Trimestral (29,99 € / 3 meses - 9,99 €/mes, ahorro 33%)**: Recomendado por academias para dominar las 52 ITCs y asegurar el aprobado a la primera.
  - **Plan Instalador Pro Vitalicio (49,99 € pago único)**: Licencia permanente sin cuotas periódicas jamás, con actualizaciones del REBT 2026+ incluidas para uso diario en obra y proyectos.
- **Matriz Comparativa de Beneficios**: Tabla detallada que compara de forma transparente el nivel Gratuito, Pro y Vitalicio.
- **FAQ Técnica y de Facturación**: Sección interactiva de preguntas frecuentes sobre cancelación, garantía Google Play, funcionamiento 100% offline y soporte multidispositivo.
- **Acceso Directo desde la App**:
  - Badge interactivo `PRO` / `FREE` en el TopAppBar principal con navegación directa.
  - Tarjeta de perfil y suscripción renovada en `SettingsScreen`.
  - Banner informativo para usuarios en modalidad gratuita en `DashboardScreen`.
- **Integración con Google Play Billing y Entorno Sandbox**:
  - Flujo de compra con `BillingManager` para `PRO_MONTHLY_PRODUCT_ID` y `PREMIUM_LIFETIME_PRODUCT_ID`.
  - Herramientas de activación rápida en modo de desarrollo (`BuildConfig.DEBUG`) para testing exhaustivo sin depender de Play Console.
  - Restauración de compras con verificación inmediata.

---

## v21.0 - 28 Septiembre 2026

### Exámenes y Simulacros
- Formato oficial consolidado: Simulacro Oficial de 40 preguntas y 90 minutos con selección estratificada real (~25% Articulado y ~75% ITCs de Categoría Básica).
- Eliminado completamente el formato descartado de 80 preguntas.
- Nuevo modo **Test por ITC**: selector completo de Articulado y de las 52 ITCs reglamentarias en bloques de 20 preguntas y 60 minutos con indicador de acierto previo y badge "Solo especialista".
- Configuración centralizada única en `ExamConfig.kt` (sin números mágicos dispersos en código).
- Desglose pedagógico por bloques (Articulado / ITCs) y nota sobre 10 en la pantalla de resultados del examen.

### Ámbito Normativo
- Delimitación del ámbito de Categoría Básica (IBTB): exclusión automática de ITCs exclusivas de especialista (`SPECIALIST_ONLY_ITC`: 6, 7, 38, 51) en los simulacros oficiales.

### Calidad y Validación
- Nuevo `QuestionValidator` con validación estricta de 4 opciones únicas, 'a' en 0..3, campos no vacíos, referencias canónicas parseables y unicidad de enunciados en todo el banco.
- Ejecución automática de validación en tests unitarios (`testDebugUnitTest`) y en `DEBUG` durante el arranque de la app.
- Extensión parseadora canónica de referencias: `Question.itcNumber()` y `Question.isArticulado()`.

### Corrección de Errores
- Corrección de `startMistakesReview`: eliminada la generación de opciones ficticias ("Opción revisada A/B/C/D"). Las preguntas obsoletas se omiten limpiamente.

### Contenido y Posits
- Incorporación de nuevas chuletas técnicas clave (previsión de cargas ITC-10, caídas de tensión ITC-14/15/19, volúmenes de baños ITC-27, tubos empotrados ITC-21, motores y descarga ITC-44/46).
- Nuevos lotes de preguntas normativas 100 % originales para ITCs prioritarias sin cobertura.

---

## v20.0 - 27 Septiembre 2026
- Reestructuración de pestañas de examen: Simulacros, Por Tema, Mis Errores e Historial.
- Consolidación del banco a 192 preguntas iniciales con referencias normativas normalizadas.
- Historial de sesiones y cálculo de porcentaje de aciertos.

## v19.0 - 27 Septiembre 2026
- Recordatorios técnicos periódicos con AlarmManager para estudio del REBT.
- Sistema de repaso espaciado para preguntas falladas.

## v18.0 - 27 Septiembre 2026
- Ampliación de cálculos de laboratorio electrotécnico (dimensionado de tubos ITC-BT-21 y puestas a tierra ITC-BT-18).
- Notificaciones locales de estudio diario.

## v17.0 - 27 Septiembre 2026
- Previsión de cargas en edificios según ITC-BT-10.
- Soporte para exportación de apuntes técnicos en posits.

## v16.0 - 27 Septiembre 2026
- Esquemas técnicos y cálculo de intensidades admisibles según UNE-HD 60364-5-52.

## v15.0 - 27 Septiembre 2026
- Integración de correlación normativa de artículos y guías técnicas BT.

## v14.0 - 27 Septiembre 2026
- Optimización de temas M3 oscuro/claro y persistencia Room de preferencias.

## v13.0 - 27 Septiembre 2026
- Calculadora de sección de conductores por caída de tensión e intensidad admisible Iz.

---

## v12.0 - 27 Septiembre 2026 (Google Play Release)

### Play Billing Library 8.0.0
- Migración obligatoria a Google Play Billing Library v8.0.0 (`billing-ktx:8.0.0`)
- Configuración de `PendingPurchasesParams` para compras únicas
- Actualizado `versionCode` a 12 y `versionName` a "12.0"
- Build verificado y optimizado para publicación en Google Play Console

## v11.1 - 27 Septiembre 2026 (Post-Audit)

### Seguridad
- Activado R8 minification para releases
- Deshabilitado allowBackup para prevenir tampering
- Fallback de Billing restringido a BuildConfig.DEBUG

### Contenido
- Corrección de auditoría: en este punto el banco contaba con 86 preguntas activas.
- Recuperadas preguntas de Suministro y Tuberías.
- Explicaciones normativas mejoradas.

### Limpieza
- Removidas dependencias muertas (OkHttp, Retrofit)

### Bugs Corregidos
- Premium gratis en error de network ✓
- Backup editable ✓
- APK decompilable ✓

## v11.0 - 27 Septiembre 2026
- Refactor arquitectónico modular
- Offline-first (sin Gemini)
- Panel admin eliminado
