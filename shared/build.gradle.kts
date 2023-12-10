plugins {
    id(libs.plugins.kotlinMultiplatform.get().pluginId)
    id(libs.plugins.androidLibrary.get().pluginId)
}

android {
    namespace = "com.mariomanzano.nadaexplorer.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}

kotlin {

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}
