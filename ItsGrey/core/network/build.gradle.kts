import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")

    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.tntt.network"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "com.tntt.network"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = 1
        versionName = "1.0"

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
}

dependencies {

    implementation(Libraries.KTX.CORE)
    implementation(Libraries.AndroidX.APP_COMPAT)
    implementation(Libraries.AndroidX.MATERIAL)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation (Libraries.Test.JUNIT)
    androidTestImplementation (Libraries.AndroidTest.ESPRESSO_CORE)

    implementation ("com.google.firebase:firebase-bom:31.2.3")
    implementation ("com.google.firebase:firebase-firestore")
    implementation ("com.google.firebase:firebase-firestore-ktx:23.0.4")
    implementation ("com.google.firebase:firebase-analytics:17.2.1")
    implementation ("com.google.firebase:firebase-database:19.2.0")

    implementation("com.google.dagger:hilt-android:${Versions.HILT}")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.HILT}")
}
