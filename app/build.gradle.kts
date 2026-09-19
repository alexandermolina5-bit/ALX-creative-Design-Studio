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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}
