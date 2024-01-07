package pl.oazapraga.oremus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class OremusApplication

fun main(args: Array<String>) {
	runApplication<OremusApplication>(*args)
}
