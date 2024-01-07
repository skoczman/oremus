package pl.oazapraga.oremus.app.domain.day.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class DayNotFound(dayId: Int) : RuntimeException("Day with id: $dayId not found")