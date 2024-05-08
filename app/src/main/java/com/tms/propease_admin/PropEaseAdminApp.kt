package com.tms.propease_admin

import android.app.Application

class PropEaseAdminApp: Application() {
    lateinit var container: Container
    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}