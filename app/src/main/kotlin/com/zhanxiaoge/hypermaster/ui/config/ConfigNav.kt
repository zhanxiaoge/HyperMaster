package com.zhanxiaoge.hypermaster.ui.config

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.zhanxiaoge.hypermaster.navigation.NavAction
import com.zhanxiaoge.hypermaster.navigation.NavScreen

fun EntryProviderScope<NavKey>.configNav(navAction: NavAction) {
    entry<NavScreen.Config> { screen ->
        ConfigScreen(
            onBack = {
                navAction.back()
            },
            onItemClick = {
                navAction.push(NavScreen.Other)
            },
        )
    }
}
