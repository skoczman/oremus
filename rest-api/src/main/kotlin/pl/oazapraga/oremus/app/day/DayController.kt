package pl.oazapraga.oremus.app.day

import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.*
import pl.oazapraga.oremus.app.reflection.ReflectionDto.ReflectionCreateRequest
import pl.oazapraga.oremus.app.reflection.ReflectionDto.ReflectionResponse
import pl.oazapraga.oremus.app.reflection.ReflectionService

@RestController
@RequestMapping("/api/day")
@CrossOrigin(origins = ["http://localhost:4200"])
class DayController(val reflectionService: ReflectionService) {

    @PostMapping("/{id}/reflection")
    @ResponseStatus(CREATED)
    fun addReflection(
        @RequestBody reflectionCreateRequest: ReflectionCreateRequest,
        @PathVariable("id") id: Int
    ): ReflectionResponse {
        return reflectionService.addReflection(id, 1, reflectionCreateRequest).asReflectionResponse()
    }
}