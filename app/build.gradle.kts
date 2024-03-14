plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
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
    val coreKtxVersion = "1.9.0"

    val legacyVersion = "1.0.0"

    val appCompatVersion = "1.6.1"
    val fragmentsVersion = "1.6.1"
    val androidLifecycleVersion = "2.6.1"

    val materialDesignVersion = "1.9.0"
    val constraintLayoutVersion = "2.1.4"
    val pieChartVersion = "0.7.0"
    val viewPagerDotsVersion = "5.0"
    val glideVersion = "4.12.0"

    val navigationVersion = "2.7.2"

    val hiltVersion = "2.48"

    val coroutineVersion = "1.3.9"

    val javaFakerVersion = "1.0.2"

    val roomVersion = "2.6.1"

    val junitVersion = "4.13.2"
    val junitExtVersion = "1.1.5"
    val espressoVersion = "3.5.1"

    //Core
    implementation("androidx.core:core-ktx:$coreKtxVersion")

    implementation("androidx.legacy:legacy-support-v4:$legacyVersion")

    implementation("androidx.appcompat:appcompat:$appCompatVersion")
    implementation("androidx.fragment:fragment-ktx:$fragmentsVersion")

    //MVVM
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$androidLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$androidLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$androidLifecycleVersion")

    //View
    implementation("com.google.android.material:material:$materialDesignVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    //Statistic diagram
    implementation("ir.mahozad.android:pie-chart:$pieChartVersion")
    //Indicator dots for ViewPager
    implementation("com.tbuonomo:dotsindicator:$viewPagerDotsVersion")
    //Image handling
    implementation("com.github.bumptech.glide:glide:$glideVersion")

    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")

    //Hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")

    //Generate data for repository
    implementation("com.github.javafaker:javafaker:$javaFakerVersion")

    //Room
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    //Tests
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$junitExtVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
}