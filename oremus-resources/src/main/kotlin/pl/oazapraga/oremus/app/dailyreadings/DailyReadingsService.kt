package pl.oazapraga.oremus.app.dailyreadings

import pl.oazapraga.oremus.app.dailyreadings.exception.DailyReadingsAlreadyExist
import pl.oazapraga.oremus.app.dailyreadings.exception.DailyReadingsNotFound
import java.time.LocalDate

class DailyReadingsService(val repository: DailyReadingsRepositoryPort) {
    fun findByDate(date: LocalDate): DailyReadings =
        repository.findByDate(date)
            .firstOrNull()
            ?: throw DailyReadingsNotFound("Readings for date $date not found")

    fun save(request: DailyReadingsCreateRequest): DailyReadings =
        repository.findByDate(request.date).firstOrNull()
            ?.let { throw DailyReadingsAlreadyExist("Readings with date: ${request.date} already exist") }
            ?: repository.save(DailyReadings(request))

    fun update(id: Int, request: DailyReadingsUpdateRequest): DailyReadings =
        repository.findById(id)
            ?.updateWith(request)
            ?.also(repository::save)
            ?: throw DailyReadingsNotFound("Readings not found")

    fun delete(id: Int) {
        repository.findById(id)?.also(repository::delete)
            ?: throw DailyReadingsNotFound("Readings not found")
    }

    private fun DailyReadings.updateWith(request: DailyReadingsUpdateRequest): DailyReadings {
        return DailyReadings(this.id, this.date, request.siglum, request.memorial)
    }

}