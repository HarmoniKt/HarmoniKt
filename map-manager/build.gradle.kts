ktor {
    fatJar {
        archiveFileName.set("map-manager.jar")
    }
}

dependencies {
    implementation(rootProject.libs.bundles.ktor.server)
    implementation(project(":common"))
}
