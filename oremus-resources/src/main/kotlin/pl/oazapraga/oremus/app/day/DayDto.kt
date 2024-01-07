package pl.oazapraga.oremus.app.day

import pl.oazapraga.oremus.app.reflection.ReflectionDto.ReflectionCreateRequest
import pl.oazapraga.oremus.app.reflection.ReflectionDto.ReflectionResponse
import java.time.LocalDate


data class Day(
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
) {
    fun update(reflectionCreateRequest: ReflectionCreateRequest): Day {
        return Day(
            id,
            date,
            author,
            reflectionCreateRequest.title,
            reflectionCreateRequest.text,
            status,
            languageRev,
            substantiveRev,
            languageRevComplete,
            substantiveRevComplete,
            editionId
        )
    }

    fun asResponse(): DayResponse {
        return DayResponse(
            id,
            date,
            author,
            title,
            text,
            status,
            languageRev,
            substantiveRev,
            languageRevComplete,
            substantiveRevComplete,
            editionId
        )
    }

    fun asReflectionResponse(): ReflectionResponse {
        return ReflectionResponse(title, text)
    }
}