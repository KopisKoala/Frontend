import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val properties= Properties().apply {
    load(File(rootProject.projectDir,"local.properties").inputStream())
}

android {
    namespace = "com.example.whashow"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.whashow"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String","NATIVE_APP_KEY",properties.getProperty("native.app.key"))
        manifestPlaceholders["NATIVE_APP_KEY"]=
            properties.getProperty("native.app.key")
        buildConfigField("String","AUTH_BASE_URL",properties.getProperty("base.url"))
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
        dataBinding=true
        viewBinding=true
        buildConfig=true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.android.identity:identity-credential-android:20231002")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("com.kakao.sdk:v2-user:2.20.1")//카카오로그인
    implementation("com.kakao.sdk:v2-all:2.20.1") // 카카오 전체 모듈 설치, 2.11.0 버전부터 지원
    //서버통신
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.8.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.8.0")
}