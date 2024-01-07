package pl.oazapraga.oremus.app.edition

import org.springframework.data.repository.findByIdOrNull
import pl.oazapraga.oremus.app.edition.exception.EditionNotFound

open class EditionRepositoryAdapter(val repository: EditionRepository) : EditionRepositoryPort {

    override fun findAll(): List<Edition> {
        return repository.findAll()
            .map(EditionEntity::toDto)
    }

    override fun findById(id: Int): Edition {
        return repository.findByIdOrNull(id)
            ?.toDto()
            ?: run { throw EditionNotFound(id) }
    }

    override fun findByDayId(dayId: Int): Edition {
        return repository.findByDayId(dayId)?.toDto()
            ?: run { throw EditionNotFound(null) }
    }

    override fun save(edition: Edition): Edition {
        return repository.save(EditionEntity(edition))
            .toDto()
    }

    override fun delete(id: Int) {
        repository.deleteById(id);
    }
}