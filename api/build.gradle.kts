plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.openapi.generator")
}

buildscript {
    dependencies {
        classpath("org.openapitools:openapi-generator-gradle-plugin:7.0.1")
    }
}

android {
    namespace = "com.usj.musicquizzapi"
    compileSdk = 35

    defaultConfig {
        minSdk = 35
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    sourceSets.getByName("main") {
        java.srcDir("client/src/main/java")
        java.srcDir("client/src/main/kotlin")
    }

    sourceSets.getByName("test") {
        java.srcDir("client/src/test/java")
        java.srcDir("client/src/test/kotlin")
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("io.gsonfire:gson-fire:1.9.0")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("io.swagger:swagger-annotations:1.6.14")
    implementation("io.github.openfeign:feign-core:13.5")
    implementation("io.github.openfeign:feign-jackson:13.5")
    implementation("io.github.openfeign:feign-slf4j:13.5")
    implementation("io.github.openfeign.form:feign-form:3.8.0")
    implementation("io.github.openfeign:feign-gson:13.5")
    implementation("com.fasterxml.jackson.core:jackson-core:2.18.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.18.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.0")
    implementation("com.github.joschi.jackson:jackson-datatype-threetenbp:2.15.2")
    implementation("org.threeten:threetenbp:1.7.0")
    implementation("com.brsanthu:migbase64:2.2")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation("androidx.annotation:annotation:1.9.1")
    implementation("androidx.core:core-ktx:1.15.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.testng:testng:6.9.6")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}

val kotlin = Action<org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension> {
    inputSpec.set("${rootDir}/app/src/main/res/raw/api.yaml")
    outputDir.set("${project.projectDir}/client")
    apiPackage.set("${android.namespace}.api")
    invokerPackage.set("${android.namespace}.invoker")
    modelPackage.set("${android.namespace}.model")
    generatorName.set("java")
    configOptions.apply {
        mapOf(
            "hideGenerationTimestamp" to "true",
            "library" to "feign",
            "feignVersion" to "10.x",
            "dateLibrary" to "threetenbp",
            "packageName" to "${android.namespace}.api",
            "enumPropertyNaming" to "original",
            "collectionType" to "list",
            "useCoroutines" to true,
            "groupId" to "${project.group}",
            "artifactId" to "client"
        )
    }
}

openApiGenerate(kotlin)