object Versions {
    const val kotlin = "1.3.70"
    const val navigation = "2.3.0-alpha04"
    const val coroutines = "1.3.5"
    const val lifecycle = "2.2.0"
    const val lifecycleTest = "2.1.0"
    const val dagger = "2.27"
    const val room = "2.2.5"
    const val mockK = "1.9.3"
    const val junitApi = "5.6.0"
    const val okhttp = "4.4.1"
    const val retrofit = "2.7.1"
    const val gson = "2.8.6"

    const val appCompat = "1.2.0-alpha03"
    const val androidCore = "1.2.0"
    const val material = "1.2.0-alpha05"
    const val constraintLayout = "2.0.0-beta4"
    const val fragment = "1.3.0-alpha02"
    const val legacySupport = "1.0.0"
    const val glide = "4.11.0"
    const val preferences = "1.1.0"
    const val livedata = "2.3.0-alpha01"
    const val coroutineTest = "1.3.4"
}

object Dependencies {
    val androidSdkVersion = 29

    val kotlinStandardLibrary = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    val navigationSafeArgsGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"

    val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    val lifecyleCore = "androidx.lifecycle:lifecycle-livedata-core-ktx:${Versions.lifecycle}"
    val lifecyleRuntime = "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle}"
    val lifecyleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    val lifecyleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    val lifeCycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    val lifeCycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.livedata}"

    val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
    val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"

    val gson = "com.google.code.gson:gson:${Versions.gson}"
    val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    val httpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    // Presentation module dependencies
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val androidCore = "androidx.core:core-ktx:${Versions.androidCore}"
    val material = "com.google.android.material:material:${Versions.material}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    val fragment = "androidx.fragment:fragment:${Versions.fragment}"
    val legacySupport = "androidx.legacy:legacy-support-v4:${Versions.legacySupport}"
    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    val preferences = "androidx.preference:preference:${Versions.preferences}"

    //testing libraries
    val mockK = "io.mockk:mockk:${Versions.mockK}"
    val junitApi = "org.junit.jupiter:junit-jupiter-api:${Versions.junitApi}"
    val junitEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junitApi}"
    val liveDataTest = "androidx.arch.core:core-testing:${Versions.lifecycleTest}"
    val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutineTest}"

}