plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "itsgrey.feature.home"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.1"
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
}

dependencies {

    implementation(project(":core:designsystem"))
    implementation(project(":domain:home"))

    implementation(Libraries.KTX.CORE)
    implementation(Libraries.AndroidX.APP_COMPAT)
    implementation(Libraries.AndroidX.MATERIAL)

    // Material Design 3
    implementation(Libraries.Compose.MATERIAL3.MATERIAL3)
    implementation(Libraries.Compose.MATERIAL3.WINDOW_SIZE)
    // Android Studio Preview support
    implementation(Libraries.Compose.UI.UI)
    implementation(Libraries.Compose.UI.PREVIEW)
    implementation(Libraries.Compose.UI.TOOLING)
    // UI Tests
    androidTestImplementation(Libraries.AndroidTest.COMPOSE_UI_TEST_JUNIT4)
    debugImplementation(Libraries.Test.COMPOSE_UI_TEST_MANIFEST)
    // the icons but not the material library (e.g. when using Material3 or a
    // custom design system based on Foundation)
    implementation(Libraries.Compose.MATERIAL.ICONS_CORE)
    implementation(Libraries.Compose.MATERIAL.ICONS_EXTENDED)

    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation (Libraries.Test.JUNIT)
    androidTestImplementation (Libraries.AndroidTest.ESPRESSO_CORE)

    implementation ("androidx.startup:startup-runtime:1.0.0")
}