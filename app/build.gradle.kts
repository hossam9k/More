import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.Properties

fun loadProperties(filePath: String): Properties {
    val properties = Properties()
    val file = rootProject.file(filePath)
    if (!file.exists()) {
        throw FileNotFoundException("Properties file not found at $filePath")
    }else{
        properties.load(FileInputStream(file))
    }
    return properties
}
// Load properties from local.properties file
val localPropertiesFilePath: String = project.findProperty("localPropertiesFilePath") as? String ?: "dev_credentials.properties"
val localProperties = loadProperties(localPropertiesFilePath)

plugins {
    alias(libs.plugins.hilt)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.poc.more"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.poc.more"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release"){
            storeFile = file(localProperties.getProperty("release_key.store"))
            storePassword =  localProperties["release_store.password"] as String
            keyAlias = localProperties["release_key.alias"] as String
            keyPassword = localProperties["release_key.password"] as String
            enableV1Signing = true
            enableV2Signing = true
        }
        getByName("debug") {
            storeFile = File(project.rootProject.rootDir,"debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            enableV1Signing = true
            enableV2Signing = true
        }
        create("releaseExternalQA"){
            storeFile = file(localProperties.getProperty("qa_key.store"))
            storePassword =  localProperties["qa_store.password"] as String
            keyAlias = localProperties["qa_key.alias"] as String
            keyPassword = localProperties["qa_key.password"] as String
            enableV1Signing = true
            enableV2Signing = true
        }
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            enableAndroidTestCoverage = true
            enableAndroidTestCoverage = true
            buildConfigField("String", "BASE_URL", "\"${localProperties["dev.debug_endpoint"]}\"")
            buildConfigField("String", "DB_VERSION", "\"${localProperties["dev.db_version"]}\"")
            buildConfigField("String", "CAN_CLEAR_CACHE", "\"${localProperties["dev.clear_cache"]}\"")
            buildConfigField("String", "MAP_KEY", "\"${localProperties["dev.map_key"]}\"")
            signingConfig = signingConfigs.getByName("debug")
        }
        create("releaseExternalQA"){
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix = ".releaseExternalQa"
            versionNameSuffix = "-QA"
            enableAndroidTestCoverage = false
            enableAndroidTestCoverage = false
            buildConfigField("String", "BASE_URL", "\"${localProperties["dev.qa_endpoint"]}\"")
            buildConfigField("String", "DB_VERSION", "\"${localProperties["dev.db_version"]}\"")
            buildConfigField("String", "CAN_CLEAR_CACHE", "\"${localProperties["dev.clear_cache"]}\"")
            buildConfigField("String", "MAP_KEY", "\"${localProperties["release.map_key"]}\"")
            signingConfig = signingConfigs.getByName("releaseExternalQA")

        }
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            enableUnitTestCoverage = false
            enableAndroidTestCoverage = false
            buildConfigField("String", "BASE_URL", "\"${localProperties["dev.prod_endpoint"]}\"")
            buildConfigField("String", "DB_VERSION", "\"${localProperties["dev.db_version"]}\"")
            buildConfigField("String", "CAN_CLEAR_CACHE", "\"${localProperties["dev.clear_cache"]}\"")
            buildConfigField("String", "MAP_KEY", "\"${localProperties["release.map_key"]}\"")
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    val app = "app"
    val store = "store"
    flavorDimensions.add(app)
    flavorDimensions.add(store)

    productFlavors {
        create("google") {
            dimension = store
            applicationIdSuffix = ".google"
            versionNameSuffix = "-google"
        }
        create("huawei") {
            dimension = store
            applicationIdSuffix = ".huawei"
            versionNameSuffix = "-huawei"
        }

        create("driver"){
            dimension = app
            applicationIdSuffix = ".driver"
            versionNameSuffix = "-driver"
        }
        create("client"){
            dimension = app
            applicationIdSuffix = ".client"
            versionNameSuffix = "-client"
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // OkHttp
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    //Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}