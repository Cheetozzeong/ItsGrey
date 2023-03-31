import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    kotlin("android")
    kotlin("kapt")
    id ("com.android.application")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services") version "4.3.10"
}

android {
    namespace = "itsgrey.app"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "com.tntt.itsgrey"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionName = "1.0.1"
        versionCode = 2

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"

            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.1"
    }
}

dependencies {

    implementation(project(":feature:editPage"))
    implementation(project(":feature:home"))

    implementation(Libraries.KTX.CORE)
    implementation(Libraries.AndroidX.APP_COMPAT)
    implementation(Libraries.AndroidX.MATERIAL)
    implementation(Libraries.AndroidX.CONSTRAINT_LAYOUT)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation(Libraries.Test.JUNIT)
    androidTestImplementation(Libraries.AndroidTest.ESPRESSO_CORE)

    // Firebase-Firestore
    implementation ("com.google.firebase:firebase-bom:31.2.3")
    implementation ("com.google.firebase:firebase-firestore")
    implementation ("com.google.firebase:firebase-firestore-ktx:23.0.4")
    implementation ("com.google.firebase:firebase-analytics:17.2.1")
    implementation ("com.google.firebase:firebase-database:19.2.0")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
    // Hilt
    implementation("com.google.dagger:hilt-android:${Versions.HILT}")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.HILT}")

    implementation(project(":domain:model"))
    implementation(project(":domain:drawing"))
    implementation(project(":domain:editbook"))
    implementation(project(":domain:editpage"))
    implementation(project(":domain:home"))
    implementation(project(":domain:login"))
    implementation(project(":domain:viewer"))

    implementation("com.github.skydoves:colorpickerview:2.2.4")

    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
}

kapt {
    correctErrorTypes = true
}