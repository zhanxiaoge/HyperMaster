package com.zhanxiaoge.hypermaster.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.StateFlow

/**
 * 局部观察 [StateFlow] 中的某个字段，避免因整体状态变化触发不必要的重组
 * @param transform 从整体状态中提取目标字段
 * @param initialValue 初始值，默认从当前 StateFlow 的最新值中提取
 */
@Composable
fun <T, R> StateFlow<T>.collectFieldAsStateWithLifecycle(
    transform: (T) -> R,
    initialValue: R = transform(this.value),
): State<R> {
    val flow = remember(this) { this.map { transform(it) }.distinctUntilChanged() }
    return flow.collectAsStateWithLifecycle(initialValue = initialValue)
}
