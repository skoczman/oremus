package pl.oazapraga.oremus.app.controller

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import pl.oazapraga.oremus.app.dailyreadings.DailyReadingsCreateRequest
import pl.oazapraga.oremus.app.dailyreadings.DailyReadingsResponse
import pl.oazapraga.oremus.app.dailyreadings.DailyReadingsService
import pl.oazapraga.oremus.app.dailyreadings.DailyReadingsUpdateRequest
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/api/daily-readings")
class DailyReadingsController(private val service: DailyReadingsService) {

    @GetMapping("/{date}")
    fun getByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate):
            DailyReadingsResponse = service.findByDate(date).asResponse()

    @PostMapping
    fun save(@RequestBody request: DailyReadingsCreateRequest):
            DailyReadingsResponse = service.save(request).asResponse()

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody request: DailyReadingsUpdateRequest):
            DailyReadingsResponse = service.update(id, request).asResponse()

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        service.delete(id)
    }
}