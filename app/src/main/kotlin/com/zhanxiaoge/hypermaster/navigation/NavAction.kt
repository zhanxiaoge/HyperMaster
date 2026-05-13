package com.zhanxiaoge.hypermaster.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.zhanxiaoge.hypermaster.utils.Throttle

/**
 * Navigation3 导航操作封装
 * 所有导航方法内置 [Throttle] 节流，防止短时间内重复触发
 * @param backStack Navigation 返回栈
 * @param throttle 节流器，避免短时间内重复触发导航操作
 */
class NavAction(
    private val backStack: NavBackStack<NavKey>,
    private val throttle: Throttle = Throttle(),
) {

    // 跳转至新页面，压入栈顶
    fun push(screen: NavScreen) = throttle.execute {
        backStack.add(screen)
    }

    // 替换当前栈顶页面
    fun replace(screen: NavScreen) = throttle.execute {
        if (backStack.isNotEmpty()) {
            backStack[backStack.lastIndex] = screen
        } else {
            backStack.add(screen)
        }
    }

    // 返回上个页面
    fun back() = throttle.execute {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
        }
    }

    // 返回指定页面，清除中间所有页面
    fun backTo(screen: NavScreen) = throttle.execute {
        val index = backStack.indexOfLast { it == screen }

        if (index != -1 && index < backStack.lastIndex) {
            backStack.subList(index + 1, backStack.size).clear()
        } else if (index == -1) {
            backStack.add(screen)
        }
    }

    // 清空栈，只保留根页面
    fun reset() = throttle.execute {
        if (backStack.size > 1) {
            backStack.subList(1, backStack.size).clear()
        }
    }

    // 清空栈，跳转至指定页面
    fun relaunch(screen: NavScreen) = throttle.execute {
        backStack.clear()
        backStack.add(screen)
    }

}
