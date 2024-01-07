plugins {
    kotlin("jvm")
    id("org.springframework.boot")
}

dependencies {
    testImplementation("org.spockframework:spock-core:2.0-M5-groovy-3.0")
    testImplementation("org.codehaus.groovy:groovy-all:3.0.9")
}