package com.zhanxiaoge.hypermaster.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * 路由清单管理
 * 使用 kotlinx.serialization 实现类型安全导航
 */

@Serializable
sealed interface NavScreen : NavKey {
    // 引导页
    @Serializable data object First : NavScreen

    // 主页
    @Serializable data object Home : NavScreen

    // 设置页
    @Serializable data object Config : NavScreen

    // 其他
    @Serializable data object Other : NavScreen
}
