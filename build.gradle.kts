
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("com.android.library") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("androidx.navigation.safeargs") version "2.6.0" apply false
}

buildscript {
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.6")
    }
    extra.apply {
        set("arch_lifecycle_version", "2.6.1")
        set("nav_version", "2.7.6")
        set("room_version", "2.6.1")
    }
}