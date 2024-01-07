package pl.oazapraga.oremus.app.edition

import pl.oazapraga.oremus.app.edition.exception.EditionNotFound
import java.util.concurrent.atomic.AtomicInteger

class EditionInMemoryRepository : EditionRepositoryPort {

    val store = mutableListOf<Edition>()
    val index = AtomicInteger()
    override fun findAll(): List<Edition> {
        return store
    }

    override fun findById(id: Int): Edition {
        return store.find { it.id == id }
            ?: throw EditionNotFound(id)
    }

    override fun findByDayId(dayId: Int): Edition {
        return store.find {
            it.days.filter { it.id == dayId }.any()
        }
            ?: throw EditionNotFound(null)
    }

    override fun save(edition: Edition): Edition {
        val toSave =
            if (edition.id > 0) {
                store.removeIf { it.id == edition.id }
                edition
            } else {
                edition.copy(id = index.incrementAndGet())
            }

        store.add(toSave)
        return findById(toSave.id)
    }

    override fun delete(id: Int) {
        store.removeIf { it.id == id }
    }
}