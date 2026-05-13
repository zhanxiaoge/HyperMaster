package com.zhanxiaoge.hypermaster.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore

private val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "hypermaster")

/**
 * 全局数据入口，持有所有业务 Manager 实例
 * 通过 [getInstance] 获取单例，确保整个应用共享同一份 DataStore
 */
class DataHub private constructor(context: Context) {

    private val dataStore = context.applicationContext.appDataStore

    // 系统配置管理器
    val system = SystemManager(dataStore)

    companion object {
        @Volatile
        private var INSTANCE: DataHub? = null

        fun getInstance(context: Context): DataHub {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DataHub(context).also {
                    INSTANCE = it
                }
            }
        }
    }

    // 智能重置：根据 [DataWhitelist] 的规则保留指定 key，清除其余所有数据
    suspend fun reset() {
        dataStore.edit { preferences ->
            val allKeys = preferences.asMap().keys

            val keysToRemove = allKeys.filter { currentKey ->
                DataWhitelist.RULES.none { it.matches(currentKey.name) }
            }

            if (keysToRemove.isNotEmpty()) {
                keysToRemove.forEach {
                    preferences.remove(it)
                }
            }
        }
    }

}
