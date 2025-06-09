dependencies {
    implementation(rootProject.libs.bundles.ktor.server)
    implementation(project(":model"))
    implementation("io.ktor:ktor-server-content-negotiation:3.0.3")
}
