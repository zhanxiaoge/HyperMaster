package com.zhanxiaoge.hypermaster.utils

import android.os.SystemClock
import java.util.concurrent.atomic.AtomicLong

/**
 * 操作节流器，在指定毫秒内重复调用会被拦截
 * @param interval 节流间隔，默认 300L（毫秒）
 */
class Throttle(private val interval: Long = 300L) {

    private val lastTimeAtomic = AtomicLong(0L)

    fun canExecute(): Boolean {
        val currentTime = SystemClock.elapsedRealtime()
        val lastTime = lastTimeAtomic.get()

        if (currentTime - lastTime < interval) {
            return false
        }

        return lastTimeAtomic.compareAndSet(lastTime, currentTime)
    }

    inline fun execute(block: () -> Unit) {
        if (canExecute()) {
            block()
        }
    }

}