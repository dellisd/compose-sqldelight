buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://www.jetbrains.com/intellij-repository/releases")
        maven(url = "https://jetbrains.bintray.com/intellij-third-party-dependencies")
    }
}

plugins {
    kotlin("multiplatform") version "1.5.10"
    id("org.jetbrains.compose") version "0.5.0-build225"
    id("com.squareup.sqldelight") version "1.5.0"
}

group = "io.github.dellisd"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(compose.web.core)
                implementation(compose.runtime)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
                implementation("com.squareup.sqldelight:sqljs-driver:1.5.0")
                implementation("com.squareup.sqldelight:coroutines-extensions:1.5.0")
                implementation(npm("copy-webpack-plugin", "^9.0.0"))
            }
        }
    }
}

sqldelight {
    database("Database") {
        packageName = "io.github.dellisd.db"
    }
}
