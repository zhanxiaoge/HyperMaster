package com.zhanxiaoge.hypermaster.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * 协程节流启动器，防止短时间内重复执行任务
 * @param block 协程执行体
 */
fun CoroutineScope.launchThrottle(block: suspend CoroutineScope.() -> Unit): () -> Unit {
    var job: Job? = null

    return {
        if (job?.isActive != true) {
            job = launch { block() }
        }
    }
}
