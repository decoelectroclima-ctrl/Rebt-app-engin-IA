// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.kotlin.compose) apply false
  alias(libs.plugins.google.devtools.ksp) apply false
  alias(libs.plugins.roborazzi) apply false
  alias(libs.plugins.secrets) apply false
}

tasks.register("generateUploadKey") {
  doLast {
    val keystoreFile = file("my-upload-key.jks")
    if (keystoreFile.exists()) {
      println("Keystore already exists at ${keystoreFile.absolutePath}")
      return@doLast
    }
    val command = listOf(
      "keytool", "-genkeypair",
      "-v",
      "-keystore", keystoreFile.absolutePath,
      "-alias", "upload",
      "-keyalg", "RSA",
      "-keysize", "2048",
      "-validity", "10000",
      "-storepass", "enginia2026",
      "-keypass", "enginia2026",
      "-dname", "CN=EnginIA, OU=REBT, O=EnginIA, L=Madrid, S=Madrid, C=ES"
    )
    val process = ProcessBuilder(command)
      .redirectErrorStream(true)
      .start()
    val output = process.inputStream.bufferedReader().readText()
    val exitCode = process.waitFor()
    if (exitCode == 0) {
      println("Successfully generated keystore at ${keystoreFile.absolutePath}")
    } else {
      error("Failed to generate keystore. Output: $output")
    }
  }
}

