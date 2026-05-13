package com.zhanxiaoge.hypermaster.data

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * DataStore 读写基类
 * - [getValue] 返回响应式 Flow，遇到 IOException 时降级为空 Preferences
 * - [setValue] 提供 suspend 写入，子类直接调用即可
 */
abstract class BaseManager(private val dataStore: DataStore<Preferences>) {

    // 统一的错误处理读取流
    @Suppress("SameParameterValue")
    protected fun <T> getValue(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[key] ?: defaultValue
        }.distinctUntilChanged()
    }

    // 统一的更新方法，子类直接调用即可
    protected suspend fun <T> setValue(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

}
