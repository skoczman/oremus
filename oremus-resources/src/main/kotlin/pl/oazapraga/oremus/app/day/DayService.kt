package pl.oazapraga.oremus.app.domain.day

import pl.oazapraga.oremus.app.day.Day
import pl.oazapraga.oremus.app.day.DayRepositoryPort


class DayService(val repository: DayRepositoryPort) {

    fun get(id: Int): Day {
        return repository.findById(id)
    }

    fun save(dto: Day) = repository.save(dto)


}