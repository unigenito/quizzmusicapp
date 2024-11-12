// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.6.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("org.openapi.generator") version "7.0.1" apply false
}

buildscript {
    dependencies {
        classpath("org.openapitools:openapi-generator-gradle-plugin:7.0.1")
    }
}