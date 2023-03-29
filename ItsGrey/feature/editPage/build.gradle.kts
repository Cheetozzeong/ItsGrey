plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.library")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.tntt.feature.editpage"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk

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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.1"
    }
}

dependencies {

    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))
    implementation(project(":domain:model"))
    implementation(project(":domain:editpage"))

    implementation(Libraries.KTX.CORE)
    implementation(Libraries.AndroidX.APP_COMPAT)
    implementation(Libraries.AndroidX.MATERIAL)

    implementation(Libraries.Compose.MATERIAL3.MATERIAL3)
    implementation(Libraries.Compose.MATERIAL3.WINDOW_SIZE)
    implementation(Libraries.Compose.UI.UI)
    implementation(Libraries.Compose.UI.PREVIEW)
    implementation(Libraries.Compose.UI.TOOLING)
    implementation(Libraries.Compose.MATERIAL.ICONS_CORE)
    implementation(Libraries.Compose.MATERIAL.ICONS_EXTENDED)

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
    implementation("androidx.navigation:navigation-compose:2.5.3")

    implementation("com.google.dagger:hilt-android:${Versions.HILT}")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.HILT}")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation(Libraries.Test.JUNIT)
    androidTestImplementation(Libraries.AndroidTest.ESPRESSO_CORE)
    androidTestImplementation(Libraries.AndroidTest.COMPOSE_UI_TEST_JUNIT4)
    debugImplementation(Libraries.Test.COMPOSE_UI_TEST_MANIFEST)
}

kapt {
    correctErrorTypes = true
}
