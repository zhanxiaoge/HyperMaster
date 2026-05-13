package com.zhanxiaoge.hypermaster.ui.home

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.zhanxiaoge.hypermaster.navigation.NavAction
import com.zhanxiaoge.hypermaster.navigation.NavScreen

fun EntryProviderScope<NavKey>.homeNav(navAction: NavAction) {
    entry<NavScreen.Home> { screen ->
        HomeScreen(
            onItemClick = {
                navAction.push(NavScreen.Config)
            }
        )
    }
}
