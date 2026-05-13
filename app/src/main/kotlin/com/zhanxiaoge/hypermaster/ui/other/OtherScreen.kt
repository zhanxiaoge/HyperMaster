package com.zhanxiaoge.hypermaster.ui.other

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.Button
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.IconButton
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.SmallTopAppBar
import top.yukonga.miuix.kmp.basic.Surface
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.extended.Back

@Composable
fun OtherScreen(
    onBack: () -> Unit,
    onDone: () -> Unit,
    viewModel: OtherViewModel = viewModel(),
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = "第四页",
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(MiuixIcons.Back, contentDescription = "返回")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                colors = ButtonDefaults.buttonColorsPrimary(),
                onClick = onDone,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp, vertical = 28.dp)
                    .navigationBarsPadding(),
            ) {
                Text("清理")
            }
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn {
                items(50) { index ->
                    BasicComponent(title = "终点页面项 $index")
                }
            }
        }
    }
}
