import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    // Hilt
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.tntt.imagebox"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(Libraries.KTX.CORE)
    implementation(Libraries.AndroidX.APP_COMPAT)
    implementation(Libraries.AndroidX.MATERIAL)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation(Libraries.Test.JUNIT)
    androidTestImplementation(Libraries.AndroidTest.ESPRESSO_CORE)

    // Firebase-Firestore
    implementation ("com.google.firebase:firebase-bom:31.2.3")
    implementation ("com.google.firebase:firebase-firestore")
    implementation ("com.google.firebase:firebase-firestore-ktx:23.0.4")
    implementation ("com.google.firebase:firebase-analytics:17.2.1")
    implementation ("com.google.firebase:firebase-database:19.2.0")
    implementation ("com.google.firebase:firebase-storage:19.2.0")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.3.3")

    implementation(project(":core:network"))
    implementation(project(":domain:model"))
    implementation(project(":domain:repo"))
    implementation(project(":data:layer"))

    // Hilt
    implementation("com.google.dagger:hilt-android:${Versions.HILT}")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.HILT}")
}