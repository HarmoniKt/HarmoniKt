ktor {
    fatJar {
        archiveFileName.set("mir-service.jar")
    }
}

dependencies {
    implementation(rootProject.libs.bundles.ktor.server)
    implementation(rootProject.libs.ktor.client.apache)
    implementation(project(":common"))
}
