ktor {
    fatJar {
        archiveFileName.set("group-mission-manager.jar")
    }
}

dependencies {
    implementation(rootProject.libs.bundles.ktor.server)
    implementation(project(":common"))
}
