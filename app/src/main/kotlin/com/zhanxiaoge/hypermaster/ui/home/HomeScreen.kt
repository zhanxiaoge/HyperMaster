package com.zhanxiaoge.hypermaster.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.NavigationBar
import top.yukonga.miuix.kmp.basic.NavigationBarItem
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.Surface
import top.yukonga.miuix.kmp.basic.TopAppBar
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.extended.Settings
import top.yukonga.miuix.kmp.icon.extended.VerticalSplit

@Composable
fun HomeScreen(
    onItemClick: () -> Unit,
    viewModel: HomeViewModel = viewModel(),
) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "主功能页",
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    label = "首页",
                    icon = MiuixIcons.VerticalSplit,
                    selected = selectedIndex == 0,
                    onClick = { selectedIndex = 0 },
                )
                NavigationBarItem(
                    label = "偏好",
                    icon = MiuixIcons.Settings,
                    selected = selectedIndex == 1,
                    onClick = { selectedIndex = 1 },
                )
            }
        }
    ) { innerPadding ->
        Surface {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding,
            ) {
                items(50) { index ->
                    BasicComponent(
                        title = "功能入口 $index (点击进入三级页)",
                        onClick = onItemClick
                    )
                }
            }
        }
    }
}
