# Changelog

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
- Recuperadas/recreadas 50+ preguntas (Suministro, Tuberías)
- Total preguntas: 130+
- Explicaciones mejoradas

### Limpieza
- Removidas dependencias muertas (OkHttp, Retrofit)

### Bugs Corregidos
- Premium gratis en error de network ✓
- Backup editável ✓
- APK decompilable ✓

## v11.0 - 27 Septiembre 2026
- Refactor arquitectónico modular
- Offline-first (sin Gemini)
- Panel admin eliminado
