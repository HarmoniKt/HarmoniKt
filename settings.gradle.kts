plugins {
    id("com.gradle.develocity") version "3.19.2"
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "2.0.21"
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

develocity {
    buildScan {
        termsOfUseUrl = "https://gradle.com/terms-of-service"
        termsOfUseAgree = "yes"
        uploadInBackground = !System.getenv("CI").toBoolean()
        publishing.onlyIf { it.buildResult.failures.isNotEmpty() }
    }
}

gitHooks {
    preCommit {
        tasks("ktlintCheck")
    }
    commitMsg { conventionalCommits() }
    createHooks()
}

rootProject.name = "HarmoniKt"
include(
    ":group-mission-manager",
    ":map-manager",
    ":common",
    ":robot-manager",
    ":mir-service",
    ":spot-service",
)
