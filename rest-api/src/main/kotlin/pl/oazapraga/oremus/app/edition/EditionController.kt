package pl.oazapraga.oremus.app.edition

import org.springframework.http.HttpStatus.ACCEPTED
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/edition")
class EditionController(val service: EditionService) {

    @GetMapping
    fun getAll(): List<EditionResponse> {
        return service.findAll().map(Edition::asResponse)
    }

    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@RequestBody edition: EditionCreateRequest): EditionResponse {
        return service.save(edition)
            .asResponse()
    }

    @PutMapping("/{id}")
    @ResponseStatus(ACCEPTED)
    fun update(@RequestBody edition: EditionUpdateRequest, @PathVariable("id") id: Int): EditionResponse {
        return service.update(edition, id)
            .asResponse()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): EditionResponse {
        return service.get(id)
            .asResponse()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        service.delete(id)
    }
}

