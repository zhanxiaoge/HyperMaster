package com.zhanxiaoge.hypermaster.main

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation3.runtime.rememberNavBackStack
import com.zhanxiaoge.hypermaster.navigation.NavAction
import com.zhanxiaoge.hypermaster.navigation.NavHub
import com.zhanxiaoge.hypermaster.navigation.NavScreen
import com.zhanxiaoge.hypermaster.utils.collectFieldAsStateWithLifecycle
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.theme.darkColorScheme
import top.yukonga.miuix.kmp.theme.lightColorScheme

/**
 * 应用主入口
 * 负责沉浸式、软键盘等系统级配置
 * 注入 MainViewModel 共享数据
 */
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.isNavigationBarContrastEnforced = false
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        setContent {
            CompositionLocalProvider(LocalMainViewModel provides mainViewModel) {
                HyperMasterApp()
            }
        }
    }

}

/**
 * 应用主题容器
 * 配置 Miuix 主题，根据系统深浅色模式自动切换主题色
 */
@Composable
private fun HyperMasterApp() {
    val colors = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()

    MiuixTheme(
        colors = colors,
        smoothRounding = false,
    ) {
        AppNavHost()
    }
}

/**
 * 启动页分发
 * 监听 [MainViewModel] 确定首个页面后锁定，防止配置变更时重复分发
 */
@Composable
private fun AppNavHost() {
    val lockedScreen = remember { mutableStateOf<NavScreen?>(null) }
    val currentScreen = lockedScreen.value

    if (currentScreen == null) {
        val mainViewModel = LocalMainViewModel.current
        val startScreen by mainViewModel.uiState.collectFieldAsStateWithLifecycle(
            transform = { it.startScreen },
            initialValue = null,
        )

        startScreen?.let { lockedScreen.value = it }

        return
    }

    AppNavContent(currentScreen)
}

/**
 * 导航堆栈实现层
 * 初始化 Navigation3 回退栈与导航操作句柄
 * @param screen 渲染指定页面
 */
@Composable
private fun AppNavContent(screen: NavScreen) {
    val backStack = rememberNavBackStack(screen)
    val navAction = remember(backStack) { NavAction(backStack) }
    NavHub(backStack, navAction)
}
