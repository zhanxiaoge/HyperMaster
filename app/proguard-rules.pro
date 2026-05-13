# ── Xposed 框架 ──
-keep,allowobfuscation class * extends io.github.libxposed.api.XposedModule { <init>(...); }
-keepattributes *Annotation*,RuntimeVisibleAnnotations,RuntimeInvisibleAnnotations,AnnotationDefault
-keepattributes SourceFile,LineNumberTable,EnclosingMethod,InnerClasses,Signature,Exceptions
-keep class com.zhanxiaoge.hypermaster.BuildConfig { *; }
-adaptresourcefilecontents META-INF/xposed/java_init.list

# ── 序列化 ──
-if @kotlinx.serialization.Serializable class **
-keep,allowshrinking,allowoptimization,allowobfuscation,allowaccessmodification class <1>
-if @kotlinx.serialization.internal.NamedCompanion class *
-keep,allowshrinking,allowoptimization,allowobfuscation,allowaccessmodification class <1>
-keepclassmembers @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# ── ViewModel ──
-keepclassmembers class * extends androidx.lifecycle.ViewModel { public <init>(...); }

# ── 枚举 ──
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ── Native ──
-keepclasseswithmembers class * { native <methods>; }

# ── R8 优化 ──
-allowaccessmodification
-repackageclasses ''

# ── 移除日志 ──
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
}

# ── Kotlin 编译器生成的断言 ──
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    public static void check*(...);
    public static void throw*(...);
}

# ── Objects.requireNonNull（Kotlin 非空参数编译产物） ──
-assumenosideeffects class java.util.Objects {
    public static ** requireNonNull(...);
}
