package pl.oazapraga.oremus.app.day

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.oazapraga.oremus.app.domain.day.DayService

@Configuration
open class DayServiceConfiguration(val repository: DayRepository) {

    @Bean
    open fun dayService(): DayService {
        val repositoryPort = DayRepositoryAdapter(repository)
        return DayService(repositoryPort)
    }
}