# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Preserve data classes for offline functionality
-keep class com.example.data.Question { *; }
-keep class com.example.data.ModuleDefinition { *; }
-keep class com.example.data.Content { *; }

# Preserve all Room entities
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao class * { *; }
-keep @androidx.room.Database class * { *; }
-keep class * extends androidx.room.RoomDatabase { *; }

# Preserve ViewModel
-keep class com.example.MainViewModel { *; }
-keep class * extends androidx.lifecycle.ViewModel { *; }

# Preserve Composables
-keep @androidx.compose.runtime.Composable class * { *; }
-keepclasseswithmembernames class * {
    @androidx.compose.runtime.Composable <methods>;
}

# Preserve Kotlin metadata
-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*
-keep class kotlin.Metadata { *; }
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
