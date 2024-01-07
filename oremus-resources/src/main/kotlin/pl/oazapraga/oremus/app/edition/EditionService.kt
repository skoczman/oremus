package pl.oazapraga.oremus.app.edition

import jakarta.transaction.Transactional
import pl.oazapraga.oremus.app.ValidationIssue.DATES_OVERLAPS_EXISTING_RANGE
import pl.oazapraga.oremus.app.ValidationIssue.NO_EDITION_EXISTS
import pl.oazapraga.oremus.app.dailyreadings.DailyReadingsService
import pl.oazapraga.oremus.app.day.Day
import pl.oazapraga.oremus.app.edition.exception.InvalidEditionRequest
import java.time.LocalDate


open class EditionService(
    val repository: EditionRepositoryPort,
    val dailyReadingsService: DailyReadingsService
) {

    @Transactional
    fun save(editionCreateRequest: EditionCreateRequest): Edition {
        val request = editionCreateRequest.copy(
            daysRangeDateFrom = editionCreateRequest.daysRangeDateFrom ?: getNextDaysRangeDateFrom()
        )

        validation(request)

        val edition = Edition(
            name = request.name,
            days = request.daysRangeDateFrom!!.createDaysUntil(request.daysRangeDateTo),
            creatingReflectionBeginningDate = request.creatingReflectionBeginningDate,
            creatingReflectionEndDate = request.creatingReflectionEndDate
        )

        return repository.save(edition)
    }

    fun update(request: EditionUpdateRequest, id: Int): Edition {
        validation(request)

        return repository.findById(id)
            .let {
                val updated = Edition(
                    it.id,
                    request.name,
                    it.creatingReflectionBeginningDate,
                    it.creatingReflectionEndDate,
                    it.days
                )
                repository.save(updated)
            }
    }

    fun findAll(): List<Edition> {
        return repository.findAll()
    }

    fun get(id: Int): Edition {
        return repository.findById(id)
    }

    fun getByDayId(dayId: Int): Edition {
        return repository.findByDayId(dayId)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    private fun validation(request: EditionCreateRequest) {
        validateRequirements(request)
        validateUniqueness(request)
    }

    private fun validation(request: EditionUpdateRequest) {
        validateRequirements(request)
    }

    private fun validateRequirements(request: EditionCreateRequest) {
        EditionValidator()
                .validate(request)
                .takeIf { it.isNotEmpty() }
                ?.let { throw InvalidEditionRequest(it) }
    }

    private fun validateRequirements(request: EditionUpdateRequest) {
        EditionValidator()
                .validate(request)
                .takeIf { it.isNotEmpty() }
                ?.let { throw InvalidEditionRequest(it) }
    }

    private fun validateUniqueness(request: EditionCreateRequest) {
        checkDatesOverlaps(repository.findAll(), request)
    }

    private fun LocalDate.createDaysUntil(creatingReflectionEndDate: LocalDate): MutableSet<Day> {
        return this.datesUntil(creatingReflectionEndDate.plusDays(1))
            .toList()
            .map { Day(date = it, author = "mockedAuthor") }
            .toMutableSet()
    }

    private fun getNextDaysRangeDateFrom() =
        repository.findAll()
            .flatMap { it.days }
            .maxOfOrNull { it.date }?.plusDays(1)
            ?: throw InvalidEditionRequest(mutableSetOf(NO_EDITION_EXISTS))


    private fun checkDatesOverlaps(editions: List<Edition>, request: EditionCreateRequest) =
        editions
            .flatMap { it.days }
            .filter { !it.date.isBefore(request.daysRangeDateFrom) && !it.date.isAfter(request.daysRangeDateTo) }
            .takeIf { it.isNotEmpty() }
            ?.run {
                throw InvalidEditionRequest(mutableSetOf(DATES_OVERLAPS_EXISTING_RANGE))
            }

    private fun getDailyReadings(date: LocalDate) =
        dailyReadingsService.findByDate(date)

}