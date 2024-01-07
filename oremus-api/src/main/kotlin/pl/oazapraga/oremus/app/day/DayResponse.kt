package pl.oazapraga.oremus.app.day

import java.time.LocalDate

data class DayResponse(
    val id: Int? = null,
    val date: LocalDate,
    val author: String? = null,
    val title: String? = null,
    val text: String? = null,
    val status: String? = null,
    val languageRev: String? = null,
    val substantiveRev: String? = null,
    val languageRevComplete: Boolean = false,
    val substantiveRevComplete: Boolean = false,
    val editionId: Int? = null
)