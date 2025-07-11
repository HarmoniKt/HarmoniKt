ktor {
    fatJar {
        archiveFileName.set("robot-manager.jar")
    }
}

dependencies {
    implementation(rootProject.libs.bundles.ktor.server)
    implementation(project(":common"))
}
