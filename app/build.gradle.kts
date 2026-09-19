plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }

android {
    namespace = "com.alx.creativestudio"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.alx.creativestudio"
        minSdk = 26
        targetSdk = 35
        versionCode = 8
        versionName = "5.3-ready"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}
