package com.zhanxiaoge.hypermaster.xposed

import android.util.Log
import io.github.libxposed.api.XposedModule
import io.github.libxposed.api.XposedModuleInterface

class HookEntry : XposedModule() {

    companion object {
        private const val TAG = "日志"
    }

    override fun onModuleLoaded(param: XposedModuleInterface.ModuleLoadedParam) {
        super.onModuleLoaded(param)
        Log.d(TAG, "onModuleLoaded: " + param.processName)
        Log.d(TAG, "framework: $frameworkName($frameworkVersionCode) API $apiVersion")
    }

    override fun onPackageLoaded(param: XposedModuleInterface.PackageLoadedParam) {
        super.onPackageLoaded(param)
        Log.d(TAG, "onPackageLoaded: " + param.packageName)
        Log.d(TAG, "default classloader is " + param.defaultClassLoader)
    }

    override fun onPackageReady(param: XposedModuleInterface.PackageReadyParam) {
        super.onPackageReady(param)
        Log.d(TAG, "onPackageReady: " + param.packageName)
        Log.d(TAG, "app classloader is " + param.classLoader)
    }

}
