import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")

    // Hilt
    kotlin("kapt")
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
    }
}

dependencies {

    implementation(Libraries.KTX.CORE)
    implementation(Libraries.AndroidX.APP_COMPAT)
    implementation(Libraries.AndroidX.MATERIAL)
    implementation(Libraries.AndroidX.CONSTRAINT_LAYOUT)
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation(Libraries.Test.JUNIT)
    androidTestImplementation(Libraries.AndroidTest.ESPRESSO_CORE)

    // Firebase-Firestore
    implementation ("com.google.firebase:firebase-bom:31.2.3")
    implementation ("com.google.firebase:firebase-firestore")
    implementation ("com.google.firebase:firebase-firestore-ktx:23.0.4")
    implementation ("com.google.firebase:firebase-analytics:17.2.1")
    implementation ("com.google.firebase:firebase-database:19.2.0")

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

}

kapt {
    correctErrorTypes = true
}
