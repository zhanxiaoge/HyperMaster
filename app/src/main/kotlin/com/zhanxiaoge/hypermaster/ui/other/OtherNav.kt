package com.zhanxiaoge.hypermaster.ui.other

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.zhanxiaoge.hypermaster.main.LocalMainViewModel
import com.zhanxiaoge.hypermaster.navigation.NavAction
import com.zhanxiaoge.hypermaster.navigation.NavScreen

fun EntryProviderScope<NavKey>.otherNav(navAction: NavAction) {
    entry<NavScreen.Other> { screen ->
        val mainViewModel = LocalMainViewModel.current
        OtherScreen(
            onBack = {
                navAction.back()
            },
            onDone = {
                mainViewModel.resetAppData()
            }
        )
    }
}
