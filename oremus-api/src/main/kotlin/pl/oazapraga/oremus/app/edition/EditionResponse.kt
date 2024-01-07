package pl.oazapraga.oremus.app.edition

import pl.oazapraga.oremus.app.day.DayResponse
import java.time.LocalDate

data class EditionResponse(
    val id: Int, val name: String, val creatingReflectionBeginningDate: LocalDate,
    val creatingReflectionEndDate: LocalDate, val days: List<DayResponse>
)