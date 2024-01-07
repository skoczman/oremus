package pl.oazapraga.oremus.app

import org.springframework.http.ResponseEntity
import pl.oazapraga.oremus.app.dto.TestReflection
import spock.lang.Ignore

import static java.time.LocalDate.now
import static org.springframework.http.HttpStatus.*

class ReflectionSpec extends BaseIntegrationTest {

    def 'should add new reflection to day'() {
        given:
            def edition = given.edition.anySavedEdition()
            edition.creatingReflectionBeginningDate = now()
            edition.creatingReflectionEndDate = now().plusDays(10)
            def dayId = edition.days[0].id
            def unsaved = given.reflection.unsaved()

        when:
            ResponseEntity postResponse = restTemplate.postForEntity("/api/day/$dayId/reflection", unsaved, TestReflection.class)

        then:
            postResponse.statusCode == CREATED

        and:
            with(postResponse.body) {
                text == unsaved.text
            }

        and:
            def fetchedDay = dayRepository.findById(dayId)
            fetchedDay.isPresent()
            with(fetchedDay.get()) {
                text == unsaved.text
            }
    }

    def 'should not add reflection if day not exists'() {
        given:
            def unsaved = given.reflection.unsaved()

        when:
            ResponseEntity postResponse = restTemplate.postForEntity("/day/1/reflection/1", unsaved, TestReflection.class)

        then:
            postResponse.statusCode == NOT_FOUND
    }

    def 'should not create reflection if submit date is after creating reflections end date'() {
        given:
            def edition = given.edition.anySavedEdition(
                    now(),
                    now().plusDays(30),
                    now().minusDays(30),
                    now().minusDays(1)
            )
            def dayId = edition.days[0].id
            def unsaved = given.reflection.unsaved()

        when:
            ResponseEntity postResponse = restTemplate.postForEntity("/api/day/$dayId/reflection", unsaved, TestReflection.class)

        then:
            postResponse.statusCode == BAD_REQUEST

    }

    //TODO: after adding authors
    @Ignore
    def 'should not create reflection if submiting author is not assigned to this day'() {
        given:
            def unsaved = given.reflection.unsaved()

        when:
            ResponseEntity postResponse = restTemplate.postForEntity("/api/day/1/reflection/1", unsaved, TestReflection.class)

        then:
            postResponse.statusCode == BAD_REQUEST

    }

}
