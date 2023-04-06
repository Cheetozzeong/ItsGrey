import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    kotlin("android")
    kotlin("kapt")
    id ("com.android.application")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "itsgrey.app"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "com.tntt.itsgrey"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionName = "2.0.1"
        versionCode = 3

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

    implementation(project(":feature:editBook"))
    implementation(project(":feature:editPage"))
    implementation(project(":feature:home"))
    implementation(project(":feature:drawing"))
    implementation(project(":feature:viewer"))
    implementation(project(":domain:home"))
    implementation(project(":core:designsystem"))

    implementation(Libraries.KTX.CORE)
    implementation(Libraries.AndroidX.APP_COMPAT)
    implementation(Libraries.AndroidX.MATERIAL)
    implementation(Libraries.AndroidX.CONSTRAINT_LAYOUT)
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation(project(mapOf("path" to ":data:layer")))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation(Libraries.Test.JUNIT)
    androidTestImplementation(Libraries.AndroidTest.ESPRESSO_CORE)

    // Firebase-Firestore
    implementation ("com.google.firebase:firebase-bom:31.4.0")
    implementation ("com.google.firebase:firebase-firestore")
    implementation ("com.google.firebase:firebase-firestore-ktx:24.4.5")
    implementation ("com.google.firebase:firebase-analytics:21.2.1")
    implementation ("com.google.firebase:firebase-database:20.1.0")
    implementation ("com.google.firebase:firebase-auth:21.2.0")
    implementation ("com.google.android.gms:play-services-auth:20.4.1")

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
    implementation(project(":data:layer"))
    implementation(project(":data:imagebox"))
    implementation(project(":domain:repo"))

    implementation("com.github.skydoves:colorpickerview:2.2.4")

    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
}

kapt {
    correctErrorTypes = true
}