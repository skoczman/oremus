////package pl.oazapraga.oremus
////
////import org.apache.commons.csv.CSVFormat
////import org.apache.commons.csv.CSVParser
////import org.springframework.beans.factory.InitializingBean
//import org.springframework.beans.factory.InitializingBean
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import pl.oazapraga.oremus.app.dailyreadings.DailyReadingsRepository
//import pl.oazapraga.oremus.app.day.DayRepository
//import pl.oazapraga.oremus.app.edition.EditionEntity
//import pl.oazapraga.oremus.app.edition.EditionRepository
//import pl.oazapraga.oremus.app.edition.EditionService
//import java.time.LocalDate
//
////import org.springframework.core.io.ClassPathResource
////import pl.oazapraga.oremus.app.dailyreadings.DailyReadingsEntity
////import pl.oazapraga.oremus.app.edition.EditionCreateRequest
////import pl.oazapraga.oremus.app.edition.EditionResponse
////import pl.oazapraga.oremus.app.edition.EditionService
////import pl.oazapraga.oremus.app.dailyreadings.DailyReadingsRepository
////import pl.oazapraga.oremus.app.day.DayRepository
////import java.io.InputStreamReader
////import java.time.LocalDate
////
////
//@Configuration
//@ConditionalOnProperty(
//    value = ["oremus.testData"],
//    havingValue = "true",
//    matchIfMissing = false
//)
//open class TestDataProvider(
//    private val dailyReadingsRepository: DailyReadingsRepository,
//    private val editionRepository: EditionRepository,
//    private val dayRepository: DayRepository,
//) {
//
//    @Bean
//    fun initData(): InitializingBean {
//        return InitializingBean {
////            importDailyReadingsFromFile()
//
//
//            editionRepository.save(
//                EditionEntity(
//                    1,
//                    "Styczeń - Marzec",
//                    LocalDate.parse("2023-01-01"),
//                    LocalDate.parse("2023-03-31"),
//                    mutableSetOf())
//                )
//////
//////            edition1.days.forEach {
//////                val dayEntity = dayRepository.findByDate(it.date).get()
//////                dayEntity.title = "example title"
//////                dayEntity.text = "example reflection text"
//////                dayEntity.languageRevComplete = true
//////                dayEntity.substantiveRevComplete = true
//////                dayRepository.save(dayEntity)
//////            }
////
////            val today = LocalDate.now()
////            val edition2 = createEdition(
////                "Aktualna",
////                today.plusDays(30),
////                today.plusDays(35),
////                today,
////                today.plusDays(25)
////            )
////
////            var i = 1
////            edition2.days.forEach {
////                i++
////                if (i % 9 == 0) {
////                    val dayEntity = dayRepository.findByDate(it.date).get()
////                    dayEntity.title = "example title"
////                    dayEntity.text = "example reflection text"
////                    dayEntity.languageRevComplete = true
////                    dayEntity.substantiveRevComplete = true
////                    dayRepository.save(dayEntity)
////                }
////            }
////
//////            val entity = EditionEntity(-1, "TestName",  LocalDate.parse("2023-02-01"),
//////                    LocalDate.parse("2023-02-28"), mutableSetOf())
//////            val saved = editionRepository.save(entity)
//////            val day = DayEntity(-1,  LocalDate.parse("2023-02-01"), null, null, null, null, null, null, false, false, null, null)
//////
//////            entity.days
//////
//////            editionRepository.save(saved)
//        }
//    }
////
//    private fun createEdition(
//    name: String,
//    daysRangeDateFrom: LocalDate,
//    daysRangeDateTo: LocalDate,
//    creatingReflectionBeginningDate: LocalDate,
//    creatingReflectionEndDate: LocalDate
//    ): EditionEntity {
//
//        return editionRepository.save(
//            EditionEntity(
//                1,
//                name,
//                daysRangeDateFrom,
//                daysRangeDateTo,
//            )
//        )
//    }
////
////
////    private fun importDailyReadingsFromFile() {
////        val resource = ClassPathResource("/sigla.csv")
////        val inputStream = resource.inputStream
////        val reader = InputStreamReader(inputStream)
////
////        val csvParser = CSVParser(
////            reader, CSVFormat.Builder.create()
////                .setDelimiter(',')
////                .setHeader()
////                .setSkipHeaderRecord(true)
////                .setQuote('\"')
////                .setTrim(true)
////                .build()
////        )
////
////        for (csvRecord in csvParser) {
////            val date = LocalDate.parse(csvRecord[0])
////            val memorial = csvRecord[2]
////            val siglum = csvRecord[3]
////
////            dailyReadingsRepository.save(
////                DailyReadingsEntity(
////                date = date,
////                siglum = siglum,
////                memorial = memorial
////            )
////            )
////        }
////    }
////}