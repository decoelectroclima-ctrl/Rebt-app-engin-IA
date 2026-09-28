package com.example.data

// Regex para capturar el número de la primera ITC mencionada en la referencia.
// Soporta formatos: "ITC-BT-18", "ITC-BT 18", "ITC-BT-05", "ITC-01", "ITC 04", "ITC-BT-21, Tabla 5", etc.
private val ITC_REGEX = """\bITC(?:-BT|\s+BT)?\s*[-–]?\s*(\d+)""".toRegex(RegexOption.IGNORE_CASE)

// Regex para detectar mención a artículos ("Art. X", "Artículo X")
private val ART_REGEX = """\bArt(?:[ií]culo)?\.?\s*\d+""".toRegex(RegexOption.IGNORE_CASE)
private val GENERAL_ART_REGEX = """\bArt(?:[ií]culo)?\b""".toRegex(RegexOption.IGNORE_CASE)

/**
 * Retorna el número de la ITC referenciada en [ref].
 *
 * DECISIÓN NORMATIVA (DOCUMENTADA):
 * Si la referencia menciona varias ITC (ej. "ITC-BT-17 e ITC-BT-24" o "ITC-BT-07 e ITC-BT-21"),
 * computa para la PRIMERA ITC identificada, dado que representa el contexto primario
 * o partida de la prescripción técnica evaluada.
 */
fun Question.itcNumber(): Int? {
    val match = ITC_REGEX.find(ref) ?: return null
    return match.groupValues[1].toIntOrNull()
}

/**
 * Retorna true si la pregunta pertenece al bloque de Articulado del REBT (Real Decreto 842/2002
 * o decretos complementarios como RD 1955/2000).
 *
 * Se identifican por contener "Art.", "Artículo" o referencias directas al articulado reglamentario.
 */
fun Question.isArticulado(): Boolean {
    if (ART_REGEX.containsMatchIn(ref) || GENERAL_ART_REGEX.containsMatchIn(ref)) {
        return true
    }
    // Si menciona el Real Decreto y no corresponde a una ITC concreta:
    if ((ref.contains("RD 842/2002", ignoreCase = true) || ref.contains("RD 1955/2000", ignoreCase = true) || ref.contains("REBT 2002", ignoreCase = true)) && itcNumber() == null) {
        return true
    }
    return false
}
