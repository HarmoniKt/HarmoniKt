plugins {
    alias(libs.plugins.dokka)
    alias(libs.plugins.gitSemVer)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.qa)
    alias(libs.plugins.ktor)
    alias(libs.plugins.serialization)
    alias(libs.plugins.taskTree)
}

group = "it.unibo.harmonikt"

val Provider<PluginDependency>.id: String get() = get().pluginId

allprojects {
    repositories {
        mavenCentral()
    }

    with(rootProject.libs.plugins) {
        apply(plugin = dokka.id)
        apply(plugin = gitSemVer.id)
        apply(plugin = kotlin.jvm.id)
        apply(plugin = kotlin.qa.id)
        apply(plugin = ktor.id)
        apply(plugin = serialization.id)
        apply(plugin = taskTree.id)
    }

    dependencies {
        implementation(rootProject.libs.bundles.arrow)
        implementation(rootProject.libs.consul.client)
        implementation(rootProject.libs.kotlin.stdlib)
        implementation(rootProject.libs.kotlin.reflect)
        implementation(rootProject.libs.kotlinx.serialization.json)
        implementation(rootProject.libs.bundles.logging)
        testImplementation(rootProject.libs.bundles.kotlin.testing)
    }

    kotlin {
        compilerOptions {
            allWarningsAsErrors = true
            freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
            optIn.add("kotlin.uuid.ExperimentalUuidApi")
        }
    }

    application {
        mainClass = "it.unibo.harmonikt.MainKt"
    }

    tasks.test {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
            showCauses = true
            showStackTraces = true
            events(
                *org.gradle.api.tasks.testing.logging.TestLogEvent
                    .values(),
            )
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }
}
