package com.zhanxiaoge.hypermaster.ui.first

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.zhanxiaoge.hypermaster.main.LocalMainViewModel
import com.zhanxiaoge.hypermaster.navigation.NavAction
import com.zhanxiaoge.hypermaster.navigation.NavScreen

fun EntryProviderScope<NavKey>.firstNav(navAction: NavAction) {
    entry<NavScreen.First> {
        val mainViewModel = LocalMainViewModel.current
        FirstScreen(
            onDone = {
                mainViewModel.setFirstRunDone()
                navAction.replace(NavScreen.Home)
            },
        )
    }
}
