plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
}

android {
    namespace = "com.myrepo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.myrepo"
        minSdk = 19
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding =  true
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.espresso.contrib)
    testImplementation(libs.junit)
    testImplementation("junit:junit:4.12")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.mockito:mockito-core:2.28.2")
    androidTestImplementation("org.mockito:mockito-android:2.24.5")
    testImplementation("com.google.truth:truth:1.1.3")
    androidTestImplementation("com.google.truth:truth:1.1.3")
    //Shimmer Effect
    implementation(libs.shimmer)

    // Coroutine Lifecycle Scopes
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Coroutine
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    //Retrofit
    implementation (libs.retrofit)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.logging.interceptor)
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.0")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.9.0")
    androidTestImplementation("com.squareup.okhttp3:okhttp:4.0.1")

    //ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx.v270)
    //Swipe Refresh
    implementation(libs.androidx.swiperefreshlayout)
    //WORK
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    androidTestImplementation("androidx.work:work-testing:2.9.0")
    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    //Room
    val roomVersion = "2.6.0"
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
}