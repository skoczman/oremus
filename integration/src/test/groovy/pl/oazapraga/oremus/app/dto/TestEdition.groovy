package pl.oazapraga.oremus.app.dto

import java.time.LocalDate


class TestEdition {
    int id
    String name
    LocalDate creatingReflectionBeginningDate
    LocalDate creatingReflectionEndDate
    List<TestDay> days
}