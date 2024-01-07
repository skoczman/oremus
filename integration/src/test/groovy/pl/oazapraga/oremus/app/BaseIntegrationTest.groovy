package pl.oazapraga.oremus.app

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import pl.oazapraga.oremus.app.preconditions.Precondition
import pl.oazapraga.oremus.app.repository.DailyReadingsRepository
import pl.oazapraga.oremus.app.repository.DayRepository
import pl.oazapraga.oremus.app.repository.EditionRepository
import spock.lang.Specification

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseIntegrationTest extends Specification {

    @Autowired
    public TestRestTemplate restTemplate

    @PersistenceContext
    public EntityManager entityManager

    @Autowired
    public DailyReadingsRepository dailyReadingsRepository

    @Autowired
    public EditionRepository editionRepository

    @Autowired
    public DayRepository dayRepository

    Precondition given = null

    def setup() {
        given = new Precondition(dailyReadingsRepository, editionRepository)
    }

    void cleanup() {
        editionRepository.deleteAll()
        dailyReadingsRepository.deleteAll()
        print(dailyReadingsRepository.findAll())
    }

    def request(Object obj) {
        def headers = new HttpHeaders().setContentType(MediaType.APPLICATION_JSON)
        return new HttpEntity(obj, headers)
    }
}
