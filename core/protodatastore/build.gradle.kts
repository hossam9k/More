plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.proto.buf.goggle)
}

android {
    namespace = "com.poc.protodatastore"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    protobuf {
        protoc {
            artifact = "com.google.protobuf:protoc:3.0.0"
        }
        generateProtoTasks {
            all().forEach { task ->
                task.plugins {
                    create("kotlin").apply {
                        option("lite")
                    }
                    create("java").apply {
                        option("lite")
                    }
                }
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.datastore)
    implementation(libs.protobuf.javalite)
    implementation(libs.protobuf.kotlin.lite)
    implementation("com.google.protobuf:protobuf-gradle-plugin:0.9.4")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}