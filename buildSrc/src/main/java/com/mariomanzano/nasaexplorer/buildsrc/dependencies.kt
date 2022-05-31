@file:Suppress("unused")

package com.mariomanzano.nasaexplorer.buildsrc

object Libs {

    const val androidGradlePlugin = "com.android.tools.build:gradle:7.1.3"
    const val gradleVersionsPlugin = "com.github.ben-manes:gradle-versions-plugin:0.42.0"

    object Kotlin {
        private const val version = "1.6.10"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"

        object Coroutines {
            private const val version = "1.6.1"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }

        object Jdk {
            const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version"
        }
    }

    object AndroidX {

        const val coreKtx = "androidx.core:core-ktx:1.7.0"
        const val appCompat = "androidx.appcompat:appcompat:1.4.1"
        const val material = "com.google.android.material:material:1.5.0"

        object Lifecycle {
            private const val version = "2.4.1"
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        }

        object Navigation {
            private const val version = "2.4.2"
            const val gradlePlugin =
                "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        }

        object Room {
            private const val version = "2.4.2"
            const val runtime = "androidx.room:room-runtime:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
        }

        object Test {
            private const val version = "1.4.0"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.3"
                const val junit = "androidx.test.ext:junit:$version"
            }

            object Espresso {
                private const val version = "3.4.0"
                const val contrib = "androidx.test.espresso:espresso-contrib:$version"
            }
        }

        object Compose {
            const val liveData = "androidx.compose.runtime:runtime-livedata:1.2.0-alpha08"
            const val navigation = "androidx.navigation:navigation-compose:2.4.2"
            const val activity = "androidx.activity:activity-compose:1.4.0"

            const val version = "1.1.1"
            const val ui = "androidx.compose.ui:ui:$version"
            const val material = "androidx.compose.material:material:$version"
            const val tooling = "androidx.compose.ui:ui-tooling-preview:$version"
            const val animation = "androidx.compose.animation:animation-graphics:$version"
            const val materialIcons = "androidx.compose.material:material-icons-extended:$version"

            object Test {
                const val uiJunit = "androidx.compose.ui:ui-test-junit4:$version"
                const val tooling = "androidx.compose.ui:ui-tooling:$version"
                const val uiManifest = "androidx.compose.ui:ui-test-manifest:$version"
            }
        }
    }

    object Glide {
        private const val version = "1.5.1"
        const val glide = "com.github.skydoves:landscapist-glide:$version"
    }

    object OkHttp3 {
        private const val version = "4.9.3"
        const val loginInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val converterGson = "com.squareup.retrofit2:converter-gson:$version"
    }

    object Arrow {
        private const val version = "1.1.2"
        const val core = "io.arrow-kt:arrow-core:$version"
    }

    object Hilt {
        const val navigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"

        private const val version = "2.41"
        const val android = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
    }

    object JUnit {
        private const val version = "4.13.2"
        const val junit = "junit:junit:$version"
    }

    const val turbine = "app.cash.turbine:turbine:0.7.0"

    object Mockito {
        const val kotlin = "org.mockito.kotlin:mockito-kotlin:4.0.0"
        const val inline = "org.mockito:mockito-inline:4.4.0"
    }

    object JavaX {
        const val inject = "javax.inject:javax.inject:1"
    }

    object Coil {
        private const val version = "1.4.0"
        const val compose = "io.coil-kt:coil-compose:$version"
    }

    object Accompanist {
        private const val version = "0.22.1-rc"
        const val pager = "com.google.accompanist:accompanist-pager:$version"
        const val indicators = "com.google.accompanist:accompanist-pager-indicators:$version"
        const val systemUiController =
            "com.google.accompanist:accompanist-systemuicontroller:$version"
        const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$version"
        const val placeHolder = "com.google.accompanist:accompanist-placeholder:$version"
    }
}