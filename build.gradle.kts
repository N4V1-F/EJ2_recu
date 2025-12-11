plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.slf4j:slf4j-reload4j:2.0.17")
    implementation("org.mariadb.jdbc:mariadb-java-client-jre6:1.6.1")
}

tasks.test {
    useJUnitPlatform()
}