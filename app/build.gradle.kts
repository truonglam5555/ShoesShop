plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.shoesshop"
    compileSdk = 34
    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.shoesshop"
        minSdk = 28
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

}

dependencies {
    implementation(libs.androidx.window)
    implementation(libs.firebase.database)


    val nav_version = "2.7.7"
    val coroutine_version = "1.6.4"
    val retrofit_version = "2.9.0"
    val okhttp_version = "4.8.0"
    val okhttp3_version = "4.9.3"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    //dimens - ssp, sdp
    implementation(libs.sdp)
    implementation(libs.ssp)

    //sticky scroll view
    implementation(libs.sticky)
    implementation(libs.circle.image)
    //Circle Indicator
    implementation(libs.indicator)
    implementation(libs.androidx.viewbinding)
    //coroutine
    implementation(libs.coroutine)
    //Glide
    implementation(libs.glide)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    Navigation
    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation(kotlin("reflect"))

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine_version")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp_version")
    // Retrofit

    implementation ("com.squareup.okhttp3:okhttp:$okhttp3_version")

    //Hilt dagger
    implementation("com.google.dagger:hilt-android:2.48.1")
    kapt("com.google.dagger:hilt-android-compiler:2.48.1")

//    OTP View
    implementation("com.github.appsfeature:otp-view:1.0")
    implementation("nl.psdcompany:duo-navigation-drawer:3.0.0")

    //FireStorage
    implementation(libs.firebase.storage)
    implementation(libs.firebase.database.ktx)

    // Email
    implementation( "com.sun.mail:android-mail:1.6.7")
    implementation( "com.sun.mail:android-activation:1.6.7")
}