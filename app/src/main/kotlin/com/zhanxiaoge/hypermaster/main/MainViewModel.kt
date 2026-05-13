package com.zhanxiaoge.hypermaster.main

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhanxiaoge.hypermaster.HyperApp
import com.zhanxiaoge.hypermaster.navigation.NavScreen
import com.zhanxiaoge.hypermaster.utils.launchThrottle
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * UI 状态容器
 * @property startScreen 应用启动后应进入的首个页面
 */
data class UiState(
    val startScreen: NavScreen? = null,
)

/**
 * 应用主入口 ViewModel
 * 向 UI 层提供应用主入口的状态数据
 */
class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    // 监听数据变化
    private val observeDataHub = viewModelScope.launchThrottle {
        HyperApp.dataHub.system.isFirstRunDone.collect { value ->
            val targetScreen = if (value) NavScreen.Home else NavScreen.First
            _uiState.update { it.copy(startScreen = targetScreen) }
            if (value) cancel()
        }
    }

    // 完成首次打开应用
    val setFirstRunDone = viewModelScope.launchThrottle {
        HyperApp.dataHub.system.setFirstRunDone(true)
    }

    // 重置应用配置
    val resetAppData = viewModelScope.launchThrottle {
        HyperApp.dataHub.reset()
    }

    init {
        observeDataHub()
    }

}

/**
 * 共享应用主入口 ViewModel 数据
 * 默认值设为 MainViewModel() 以保证 Preview 预览和未注入环境下的稳定性
 * 在 MainActivity 中通过 CompositionLocalProvider 注入真正的 Activity 实例
 */
val LocalMainViewModel = staticCompositionLocalOf { MainViewModel() }
