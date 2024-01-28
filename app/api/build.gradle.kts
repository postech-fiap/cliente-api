dependencies {
    implementation(project(":app:domain"))
    implementation(project(":app:infrastructure"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("io.cucumber:cucumber-java:7.15.0")
    testImplementation("io.cucumber:cucumber-junit:7.15.0")
    testImplementation("io.cucumber:cucumber-spring:7.15.0")
    testImplementation("io.cucumber:cucumber-java8:7.15.0")
    testImplementation("io.rest-assured:rest-assured:5.4.0")
}
