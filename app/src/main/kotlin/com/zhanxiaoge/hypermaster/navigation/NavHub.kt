package com.zhanxiaoge.hypermaster.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.zhanxiaoge.hypermaster.ui.config.configNav
import com.zhanxiaoge.hypermaster.ui.first.firstNav
import com.zhanxiaoge.hypermaster.ui.home.homeNav
import com.zhanxiaoge.hypermaster.ui.other.otherNav

/**
 * Navigation3 导航中心
 * 注册所有页面节点，配置状态保存与 ViewModel 生命周期
 * @param backStack Navigation 返回栈
 * @param navAction 导航操作封装
 */
@Composable
fun NavHub(
    backStack: NavBackStack<NavKey>,
    navAction: NavAction,
) {
    val saveableDecorator = rememberSaveableStateHolderNavEntryDecorator<NavKey>()
    val viewModelDecorator = rememberViewModelStoreNavEntryDecorator<NavKey>()

    val decorators = remember(saveableDecorator, viewModelDecorator) {
        listOf(saveableDecorator, viewModelDecorator)
    }

    NavDisplay(
        backStack = backStack,
        onBack = { navAction.back() },
        entryDecorators = decorators,
        entryProvider = entryProvider {
            firstNav(navAction)
            homeNav(navAction)
            configNav(navAction)
            otherNav(navAction)
        },
    )
}
