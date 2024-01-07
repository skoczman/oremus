package pl.oazapraga.oremus.app.reflection

import org.springframework.stereotype.Service
import pl.oazapraga.oremus.app.day.Day
import pl.oazapraga.oremus.app.domain.day.DayService
import pl.oazapraga.oremus.app.edition.EditionService
import pl.oazapraga.oremus.app.reflection.ReflectionDto.ReflectionCreateRequest
import pl.oazapraga.oremus.app.reflection.exception.InvalidReflectionRequest

@Service
class ReflectionService(
    val dayService: DayService,
    val editionService: EditionService
) {

    fun addReflection(dayId: Int, authorId: Int, reflectionCreateRequest: ReflectionCreateRequest): Day {
        // TODO: refactor it
        findDayById(dayId)

        validation(reflectionCreateRequest, dayId)

        return findDayById(dayId)
            .let {
                val updated = it.update(reflectionCreateRequest)
                dayService.save(updated)
            }
    }

    private fun validation(reflection: ReflectionCreateRequest, dayId: Int) {
        val edition = editionService.getByDayId(dayId)

        ReflectionValidator().validate(reflection, edition)
            .takeIf { it.isNotEmpty() }
            ?.let { throw InvalidReflectionRequest(it) }
    }

    private fun findDayById(id: Int): Day {
        return dayService.get(id)
    }
}