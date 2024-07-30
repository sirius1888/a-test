plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kapt)
    alias(libs.plugins.dagger.hilt.android)
//    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.a_test_app"
    compileSdk = libs.versions.androidSdk.compile.get().toInt()

    defaultConfig {
        applicationId = "com.example.a_test_app"
        minSdk = libs.versions.androidSdk.min.get().toInt()
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
//    implementation(projects.core.data)
//    implementation(projects.core.api)
//    implementation(projects.core.common)
//    api(projects.core.data)

    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

//    debugImplementation(libs.okhttp.logging.interceptor)

//    implementation(libs.androidx.lifecycle.runtime.ktx.v240)
//    implementation(libs.kotlinx.coroutines.android.v152)
//    implementation(libs.coil)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}