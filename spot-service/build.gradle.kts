ktor {
    fatJar {
        archiveFileName.set("spot-service.jar")
    }
}

dependencies {
    implementation(rootProject.libs.bundles.ktor.server)
    implementation(project(":common"))
    implementation("io.ktor:ktor-server-content-negotiation:3.1.3")
}
