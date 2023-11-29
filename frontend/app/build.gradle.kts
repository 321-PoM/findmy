plugins {
    id("com.android.application")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.example.findmy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.findmy"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        android.buildFeatures.buildConfig = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("java.util.concurrent.atomic.AtomicBoolean", "IS_TESTING", "new java.util.concurrent.atomic.AtomicBoolean(false)")
    }

    buildFeatures {
        buildConfig = true
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
    buildFeatures {
        viewBinding = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    configurations.all {
        resolutionStrategy {
            force("androidx.tracing:tracing:1.1.0")
        }
    }

}

dependencies {
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment:2.7.2")
    implementation("androidx.navigation:navigation-ui:2.7.2")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    val fragmentVersion = "1.6.1"

    implementation("androidx.fragment:fragment:$fragmentVersion")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-base:18.2.0")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.0-alpha01")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.0-alpha01")
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    implementation("com.squareup.okhttp3:okhttp:4.1.0")

    testImplementation("org.json:json:20180813")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")

    androidTestImplementation("androidx.tracing:tracing:1.1.0")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test:rules:1.6.0-alpha01")

    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.3.0-alpha03")

    implementation("com.github.bumptech.glide:glide:4.11.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.11.0")
}
