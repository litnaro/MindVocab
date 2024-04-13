plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt)

    //TODO version catalog
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
    kotlin("kapt")
}

android {
    namespace = "com.example.mindvocab"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mindvocab"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }

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
        viewBinding = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    //Core
    implementation(libs.androidx.ktx)

    implementation(libs.legacy)

    implementation(libs.appcompat)
    implementation(libs.fragments)

    //MVVM
    implementation(libs.lifecicle.livedata)
    implementation(libs.lifecicle.view.model)
    implementation(libs.lifecicle.runtime)

    //View
    implementation(libs.material.design)
    implementation(libs.constraint.layout)

    implementation(libs.diagram)
    implementation(libs.view.pager.indicator)
    implementation(libs.glide)
    implementation(libs.calendar)

    //Navigation
    implementation(libs.navigation.ui)
    implementation(libs.navigation.fragment)

    //Hilt
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    //Coroutines
    implementation(libs.coroutines)

    //Generate data for repository
    implementation(libs.java.faker)

    //Room
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)

    //Shimmer
    implementation(libs.shimmer)

    //Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.ext)
    androidTestImplementation(libs.androidx.espresso.core)
}