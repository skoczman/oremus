package pl.oazapraga.oremus.app.edition

import pl.oazapraga.oremus.app.day.Day
import java.time.LocalDate

data class Edition(
    val id: Int = 0,
    val name: String,
    val creatingReflectionBeginningDate: LocalDate,
    val creatingReflectionEndDate: LocalDate,
    var days: MutableSet<Day>
) {
    fun asResponse(): EditionResponse {
        return EditionResponse(
            id,
            name,
            creatingReflectionBeginningDate,
            creatingReflectionEndDate,
            days.map(Day::asResponse)
        )
    }
}