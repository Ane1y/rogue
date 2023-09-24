plugins {
    java
    application
}

group = "ru.itmo.rogue"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/com.googlecode.lanterna/lanterna
    implementation("com.googlecode.lanterna:lanterna:3.0.1")
    implementation("org.jetbrains:annotations:23.0.0")
    implementation("org.jetbrains:annotations:23.0.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("ru.itmo.rogue.App")
}
