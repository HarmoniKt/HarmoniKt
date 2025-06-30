dependencies {
    implementation(rootProject.libs.bundles.ktor.server)
    implementation(project(":common"))
    implementation("io.ktor:ktor-server-content-negotiation:3.0.3")
}
