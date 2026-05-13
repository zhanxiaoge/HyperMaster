package com.zhanxiaoge.hypermaster.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

// 系统配置管理器
class SystemManager(dataStore: DataStore<Preferences>) : BaseManager(dataStore) {

    // 是否首次运行完成
    val isFirstRunDone: Flow<Boolean> = getValue(DataKeys.System.IS_FIRST_RUN_DONE, false)

    // 设置首次运行完成
    suspend fun setFirstRunDone(enabled: Boolean) {
        setValue(DataKeys.System.IS_FIRST_RUN_DONE, enabled)
    }

}
