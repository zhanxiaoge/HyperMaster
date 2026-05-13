package com.zhanxiaoge.hypermaster

import android.app.Application
import com.zhanxiaoge.hypermaster.data.DataHub

class HyperApp : Application() {

    companion object {
        private lateinit var instance: HyperApp
        val dataHub: DataHub by lazy {
            DataHub.getInstance(instance)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}
