package com.zhanxiaoge.hypermaster.data

import androidx.datastore.preferences.core.booleanPreferencesKey

// 集中管理所有 Preferences Key，避免硬编码字符串分散在各处
object DataKeys {

    // 系统配置管理器
    object System {
        // 是否首次运行完成
        val IS_FIRST_RUN_DONE = booleanPreferencesKey("sys_is_first_run_done")
    }

}
