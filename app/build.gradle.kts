buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.52")
    }
}
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "1.6.10"

}

apply(plugin = "com.android.application")
apply(plugin = "com.google.dagger.hilt.android")

android {
    namespace = "com.example.joiefull"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.joiefull"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    kapt {

        correctErrorTypes = true

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"

    }
    packaging {


        resources {
            excludes += setOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "META-INF/gradle/incremental.annotation.processors"
            )
        }
    }



}


    dependencies {






        implementation (libs.androidx.core.splashscreen)


        implementation(libs.coil.compose)


        implementation (libs.androidx.navigation.compose)




        implementation(libs.androidx.navigation.compose.v281)
        implementation(libs.androidx.navigation.fragment)
        implementation(libs.androidx.navigation.ui)
        implementation(libs.androidx.navigation.dynamic.features.fragment)
        androidTestImplementation(libs.androidx.navigation.testing)




        implementation(libs.androidx.activity.ktx)
        implementation(libs.androidx.fragment.ktx.v183)


        implementation(libs.jetbrains.kotlinx.serialization.json.v150)
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.retrofit)
        implementation(libs.converter.gson)
        implementation(libs.retrofit2.kotlin.coroutines.adapter)
        implementation(libs.kotlinx.serialization.json)



        implementation(libs.androidx.fragment)

        implementation(libs.androidx.fragment.compose)

        debugImplementation(libs.androidx.fragment.testing)


        implementation(("androidx.hilt:hilt-navigation-compose:1.0.0"))

        implementation ("com.google.dagger:hilt-android:2.52")
        kapt ("com.google.dagger:hilt-compiler:2.52")

        // For instrumentation tests
        androidTestImplementation  ("com.google.dagger:hilt-android-testing:2.52")
        kaptAndroidTest ("com.google.dagger:hilt-compiler:2.52")

        // For local unit tests
        testImplementation ("com.google.dagger:hilt-android-testing:2.52")
        kaptTest ("com.google.dagger:hilt-compiler:2.52")


        implementation ("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha03")


        testImplementation(libs.mockk)
        testImplementation(libs.mockito.kotlin)
        testImplementation(libs.androidx.core.testing)

        implementation(libs.gson)


        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)
    }


apply(plugin = "dagger.hilt.android.plugin")

