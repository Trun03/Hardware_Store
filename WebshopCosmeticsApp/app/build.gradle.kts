plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")

}

android {
    namespace = "com.example.webshopcosmetics"
    compileSdk = 34 // Cập nhật lên 34

    defaultConfig {
        applicationId = "com.example.webshopcosmetics"
        minSdk = 24
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3" // Cập nhật thành phiên bản mới nhất nếu cần
    }
}

dependencies {


    implementation ("com.squareup.okhttp3:okhttp:4.10.0")  // Phiên bản OkHttp mới nhất

    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-ktx:1.13.0") // Cập nhật lên phiên bản mới nhất
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7") // Thư viện Lifecycle
    implementation("androidx.activity:activity-compose:1.9.3") // Thư viện Activity cho Compose
    implementation(platform("androidx.compose:compose-bom:2023.03.00")) // BOM cho Compose
    implementation("androidx.compose.ui:ui") // Thư viện UI của Compose
    implementation("androidx.compose.material3:material3") // Thư viện Material3 cho Compose
    implementation("androidx.compose.ui:ui-tooling-preview") // Cho phép hiển thị trước
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Thư viện Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Converter Gson cho Retrofit
    implementation("io.coil-kt:coil-compose:2.2.2") // Thư viện Coil để tải hình ảnh
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    testImplementation("junit:junit:4.13.2") // Thư viện kiểm thử

    androidTestImplementation("androidx.test.ext:junit:1.1.5") // Thư viện kiểm thử Android
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Thư viện Espresso cho kiểm thử UI
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00")) // BOM cho Compose trong kiểm thử
    androidTestImplementation("androidx.compose.ui:ui-test-junit4") // Kiểm thử Compose
    debugImplementation("androidx.compose.ui:ui-tooling") // Công cụ cho debug Compose
    debugImplementation("androidx.compose.ui:ui-test-manifest") // Manifest cho kiểm thử
}
