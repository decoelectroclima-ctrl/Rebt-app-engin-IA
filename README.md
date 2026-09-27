<div align="center">
<img width="1200" height="475" alt="GHBanner" src="https://ai.google.dev/static/site-assets/images/share-ais-513315318.png" />
</div>

# EnginIA REBT — App Offline de Estudio

**Versión:** 12.0 (Play Billing 8.0.0 Ready)

## Arquitectura
- 100% Offline (excepto Google Play Billing v8.0.0)
- Content.kt: 130+ preguntas estáticas
- Room Database: Progreso usuario + historiales
- Jetpack Compose: UI modular (6 screens)

## Cambios Recientes (v11.1)
✅ Activado R8 minification
✅ Deshabilitado allowBackup
✅ Protegido Billing fallback (solo debug)
✅ Agregadas 50+ preguntas (Suministro + Tuberías)
✅ Removidas dependencias muertas

## Seguridad
- APK ofuscado (R8)
- Datos locales protegidos (no-backup)
- Verificación de compras: Google Play Billing API
- Fallback limitado a builds debug

## Instrucciones Build
```bash
./gradlew assembleRelease  # Minified, ofuscado, listo para Play Store
./gradlew assembleDebug    # Debug, con fallback de Billing para testing
```
