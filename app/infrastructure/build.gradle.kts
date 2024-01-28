dependencies {
    implementation(project(":app:domain"))

    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:4.12.0")
}
