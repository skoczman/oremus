package pl.oazapraga.oremus.app.edition

interface EditionRepositoryPort {

    fun findAll(): List<Edition>
    fun findById(id: Int): Edition
    fun findByDayId(dayId: Int): Edition
    fun save(edition: Edition): Edition
    fun delete(id: Int)

}