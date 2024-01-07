package pl.oazapraga.oremus.app.edition

import java.time.LocalDate

data class EditionCreateRequest(
    val name: String, val daysRangeDateFrom: LocalDate?, val daysRangeDateTo: LocalDate,
    val creatingReflectionBeginningDate: LocalDate,
    val creatingReflectionEndDate: LocalDate
)

data class EditionUpdateRequest(val name: String)
