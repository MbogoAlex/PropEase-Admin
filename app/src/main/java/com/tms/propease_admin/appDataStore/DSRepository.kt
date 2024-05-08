package com.tms.propease_admin.appDataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DSRepository(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val USER_ID = intPreferencesKey("userId")
        private val USER_NAME = stringPreferencesKey("userName")
        private val PHONE_NUMBER = stringPreferencesKey("phoneNumber")
        private val EMAIL = stringPreferencesKey("email")
        private val PASSWORD = stringPreferencesKey("password")
        private val TOKEN = stringPreferencesKey("token")
        private val PROFILE_PICTURE = stringPreferencesKey("profilePic")
        private val APPROVAL_STATUS = stringPreferencesKey("approvalStatus")
    }

    suspend fun saveUserData(dsUserModel: DSUserModel) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = dsUserModel.userId!!
            preferences[USER_NAME] = dsUserModel.userName
            preferences[PHONE_NUMBER] = dsUserModel.phoneNumber
            preferences[EMAIL] = dsUserModel.email
            preferences[PASSWORD] = dsUserModel.password
            preferences[TOKEN] = dsUserModel.token
            preferences[APPROVAL_STATUS] = dsUserModel.approvalStatus
        }
    }

    suspend fun saveUserProfilePicture(userProfilePicture: String?) {
        dataStore.edit { preferences ->
            preferences[PROFILE_PICTURE] = userProfilePicture!!
        }
    }

    val userProfilePicture: Flow<String?> = dataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            it[PROFILE_PICTURE] ?: null
        }

    val dsUserModel: Flow<DSUserModel> = dataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            it.toDsUserModel(
                userId = it[USER_ID] ?: null,
                userName = it[USER_NAME] ?: "",
                phoneNumber = it[PHONE_NUMBER] ?: "",
                email = it[EMAIL] ?: "",
                password = it[PASSWORD] ?: "",
                token = it[TOKEN] ?: "",
                approvalStatus = it[APPROVAL_STATUS] ?: ""
            )
        }

    suspend fun deletePreferences() {
        dataStore.edit {
            it.clear()
        }
    }

    fun Preferences.toDsUserModel(
        userId: Int?,
        userName: String,
        phoneNumber: String,
        email: String,
        password: String,
        token: String,
        approvalStatus: String,
    ): DSUserModel = DSUserModel(
        userId = userId,
        userName = userName,
        phoneNumber = phoneNumber,
        email = email,
        password = password,
        token = token,
        approvalStatus = approvalStatus
    )
}