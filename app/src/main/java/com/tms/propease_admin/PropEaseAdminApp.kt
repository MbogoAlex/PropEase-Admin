package com.tms.propease_admin

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.tms.propease_admin.appDataStore.DSRepository

private val DS_NAME = "PROP_EASE_ADMIN_DS"
private val Context.datastore: DataStore<Preferences> by preferencesDataStore(
    name = DS_NAME
)
class PropEaseAdminApp: Application() {
    lateinit var container: Container
    lateinit var dsRepository: DSRepository
    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
        dsRepository = DSRepository(datastore)
    }
}