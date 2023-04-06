import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.library")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "itsgrey.feature.editbook"
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
    implementation("androidx.navigation:navigation-runtime-ktx:2.5.3")
    // UI Tests
    androidTestImplementation(Libraries.AndroidTest.COMPOSE_UI_TEST_JUNIT4)
    debugImplementation(Libraries.Test.COMPOSE_UI_TEST_MANIFEST)
    // the icons but not the material library (e.g. when using Material3 or a
    // custom design system based on Foundation)
    implementation(Libraries.Compose.MATERIAL.ICONS_CORE)
    implementation(Libraries.Compose.MATERIAL.ICONS_EXTENDED)

    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")

    // navigation
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    //Hilt
    implementation("com.google.dagger:hilt-android:${Versions.HILT}")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.HILT}")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation(Libraries.Test.JUNIT)
    androidTestImplementation(Libraries.AndroidTest.ESPRESSO_CORE)

    implementation ("com.google.accompanist:accompanist-pager:0.20.1")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.20.1")

    // 드래그 앤 드롭 외부 라이브러리
    implementation("org.burnoutcrew.composereorderable:reorderable:0.9.6")


    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":domain:editbook"))
    implementation(project(":domain:model"))
}

kapt {
    correctErrorTypes = true
}