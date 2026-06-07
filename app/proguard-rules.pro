# Keep the Composable functions from being obfuscated
-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
}

# Keep the annotations used by Compose
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Keep Compose runtime classes
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.ui.** { *; }
-keep class androidx.compose.foundation.** { *; }
-keep class androidx.compose.material.** { *; }
-keep class androidx.wear.compose.material.** { *; }
