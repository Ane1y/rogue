rootProject.name = "app"
include("src:test:java.ru.itmo.rogue")
findProject(":src:test:java.ru.itmo.rogue")?.name = "java.ru.itmo.rogue"
