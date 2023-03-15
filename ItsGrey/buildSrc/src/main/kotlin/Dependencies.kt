object Versions {

    // AndroidX
    const val APP_COMPAT = "1.6.1"
    const val MATERIAL = "1.8.0"
    const val CONSTRAINT_LAYOUT = "2.1.4"

    // KTX
    const val CORE = "1.9.0"

    // TEST
    const val JUNIT = "1.1.5"

    // Android Test
    const val ESPRESSO_CORE = "3.5.1"

    // Hilt
    const val HILT_VERSION = "2.28-alpha"

    // Kotlin
    const val KOTLIN = "1.5.31"

    object Compose {
        const val MATERIAL3 = "1.0.1"
        const val MATERIAL = "1.3.1"
        const val UI = "1.3.3"
    }
}

object Libraries {

    object AndroidX {
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
        const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    }

    object KTX {
        const val CORE = "androidx.core:core-ktx:${Versions.CORE}"
    }

    object Test {
        const val JUNIT = "androidx.test.ext:junit:${Versions.JUNIT}"
        const val COMPOSE_UI_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest:${Versions.Compose.UI}"
    }

    object AndroidTest {
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
        const val COMPOSE_UI_TEST_JUNIT4 = "androidx.compose.ui:ui-test-junit4:${Versions.Compose.UI}"
    }

    object Compose {
        object MATERIAL3 {
            const val MATERIAL3 = "androidx.compose.material3:material3:${Versions.Compose.MATERIAL3}"
            const val WINDOW_SIZE = "androidx.compose.material3:material3-window-size-class:${Versions.Compose.MATERIAL3}"
        }

        object MATERIAL {
            const val ICONS_CORE = "androidx.compose.material:material-icons-core:${Versions.Compose.MATERIAL}"
            const val ICONS_EXTENDED = "androidx.compose.material:material-icons-extended:${Versions.Compose.MATERIAL}"
        }

        object UI {
            const val UI = "androidx.compose.ui:ui:${Versions.Compose.UI}"
            const val PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Versions.Compose.UI}"
            const val TOOLING = "androidx.compose.ui:ui-tooling:${Versions.Compose.UI}"
        }
    }
}