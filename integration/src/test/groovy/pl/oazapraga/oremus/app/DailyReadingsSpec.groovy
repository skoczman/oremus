package pl.oazapraga.oremus.app

import org.springframework.http.HttpStatus
import pl.oazapraga.oremus.app.domain.dailyreadings.DailyReadingsEntity
import pl.oazapraga.oremus.app.dto.TestDailyReadings

import java.time.LocalDate

import static java.time.LocalDate.parse
import static org.springframework.http.HttpMethod.PUT

class DailyReadingsSpec extends BaseIntegrationTest {

    def "should return proper readings data by date"() {
        given:
            def dailyReadings = given.dailyReadings.anyDailyReading()

        when:
            def response = restTemplate.getForEntity("/api/daily-readings/${dailyReadings.date}", TestDailyReadings.class)

        then:
            response.statusCode == HttpStatus.OK
            with(response.body) {
                siglum == dailyReadings.siglum
                memorial == dailyReadings.memorial
            }
    }

    def "should save new Readings data"() {
        given:
            def unsavedDailyReadings = given.dailyReadings.unsaved()

        when:
            def response = restTemplate.postForEntity("/api/daily-readings", request(unsavedDailyReadings), TestDailyReadings.class)

        then:
            response.statusCode == HttpStatus.OK
            response.body != null

        and:
            def created = entityManager.find(DailyReadingsEntity, response.body.id)
            with(created) {
                date == unsavedDailyReadings.date
                siglum == unsavedDailyReadings.siglum
                memorial == unsavedDailyReadings.memorial
            }
    }

    def "should update Readings data"() {
        given:
            def dayId = given.dailyReadings.anyDailyReading().id
            def update = given.dailyReadings.updateOf(
                    "Lb 7, 10-20; Ps 67 (66), 2-3. 5; Ga 4, 4-7; Łk 2, 16-21", null)

        when:
            def response = restTemplate.exchange("/api/daily-readings/${dayId}", PUT, request(update), TestDailyReadings.class)

        then:
            response.statusCode == HttpStatus.OK
            def found = entityManager.find(DailyReadingsEntity, dayId)
            found.siglum == update.siglum
            found.memorial == update.memorial
    }

    def "should delete item"() {
        given:
            def dayId = given.dailyReadings.anyDailyReading().id

        when:
            restTemplate.delete("/api/daily-readings/${dayId}")

        then:
            entityManager.find(DailyReadingsEntity, dayId) == null
    }

    def "should return 404 when Readings item does not exist"() {
        given:
            def dailyReadings = given.dailyReadings.anyDailyReading()
            LocalDate wrongDate = dailyReadings.date.plusDays(2)

        when:
            def response = restTemplate.getForEntity("/api/daily-readings/${wrongDate}", Object.class)

        then:
            response.statusCode == HttpStatus.NOT_FOUND
            response.body.message.contains("Readings for date ${wrongDate} not found")
    }

    def "should return error when Readings with same date already exist"() {
        given:
            def givenDate = parse("2023-01-03")
            def dailyReadings = given.dailyReadings.anyDailyReading(givenDate)
            def unsavedDailyReadings = given.dailyReadings.unsaved(givenDate)

        when:
            def response = restTemplate.postForEntity("/api/daily-readings", request(unsavedDailyReadings), Object.class)

        then:
            response.statusCode == HttpStatus.BAD_REQUEST
            response.body.message.contains("Readings with date: ${dailyReadings.date} already exist")
    }
}
