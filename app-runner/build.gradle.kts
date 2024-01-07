plugins {
    kotlin("jvm")
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":jpa-repository"))
    implementation(project(":oremus-resources"))
    implementation(project(":rest-api"))
    implementation(project(":utils"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    runtimeOnly("org.postgresql:postgresql:42.6.0")
}

springBoot {
    mainClass.set("pl.oazapraga.oremus.OremusApplicationKt")
}
