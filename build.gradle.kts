plugins {
    alias(libs.plugins.dokka)
    alias(libs.plugins.gitSemVer)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.qa)
    alias(libs.plugins.multiJvmTesting)
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
        apply(plugin = multiJvmTesting.id)
        apply(plugin = taskTree.id)
    }

    dependencies {
        implementation(rootProject.libs.kotlin.stdlib)
        implementation(rootProject.libs.kotlin.reflect)
        testImplementation(rootProject.libs.bundles.kotlin.testing)
    }

    kotlin {
        compilerOptions {
            allWarningsAsErrors = true
            freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
        }
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
