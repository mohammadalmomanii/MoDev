plugins {
    id("com.android.library")
    id("maven-publish")
}

android {
    namespace = "com.mohammadalmomani.modevlib"
    compileSdk = 34

    dataBinding {
        enable = true
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
    defaultConfig {
        minSdk = 27

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")


    /*********************|Get Image via url|*********************/
    implementation("com.github.bumptech.glide:glide:4.16.0")

//    /*********************|View Model|*********************/
//    implementation("androidx.legacy:legacy-support-v4:1.0.0")
//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

//    /*********************|generate excl file|*********************/
//    implementation("org.apache.poi:poi:5.2.3")
//    implementation("org.apache.poi:poi-ooxml:5.2.3")

//    /*********************|generate pdf file|*********************/
//    implementation("com.itextpdf:itextpdf:5.5.13.2")
//    implementation("androidx.print:print:1.0.0")
}

// Maven publishing configuration
publishing {
    publications {
        create<MavenPublication>("release") {

            groupId = "com.mohammadalmomani.modevlib"
            artifactId = "modevlib"
            version = "1.2.6"

            // Optional: Customize other publication settings if needed
            afterEvaluate {
                from(components["release"])
            }
        }
    }

}