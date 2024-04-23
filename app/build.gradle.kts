import com.android.zipflinger.Sources.dir
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.include

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
        // id("androidx.navigation.safeargs")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.studentdata"
    compileSdk = 34
    buildFeatures {

        viewBinding = true
        dataBinding=true

    }
    defaultConfig {
        applicationId = "com.example.studentdata"
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.android.support:support-annotations:28.0.0")
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
   implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

    implementation("androidx.room:room-runtime:2.6.1")
   // annotationProcessor("androidx.room:room-compiler:2.6.1")

     kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    //implementation("fileTree(dir(libs, include('sqlite-jdbc-3.41.2.2.jar')")
    //implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("sqlite-jdbc-3.41.2.2.jar"))))
    implementation("org.xerial:sqlite-jdbc:3.41.2.2")

    implementation("androidx.recyclerview:recyclerview:1.3.2")

    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.6")

    implementation("com.hbb20:ccp:2.6.0")




}