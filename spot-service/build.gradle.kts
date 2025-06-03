dependencies {
    implementation(rootProject.libs.bundles.ktor.server)
    implementation(project(":model"))
    implementation("io.ktor:ktor-server-content-negotiation:3.0.3")
    implementation("io.ktor:ktor-serialization-gson:3.0.3")
    implementation("io.ktor:ktor-server-content-negotiation:3.0.3")
}
