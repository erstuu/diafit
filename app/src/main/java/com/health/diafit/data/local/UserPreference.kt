package com.health.diafit.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.health.diafit.data.remote.response.User
import com.health.diafit.data.remote.response.UserSession
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class UserPreference @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveSession(user: UserSession) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = user.name
            preferences[USER_TOKEN] = user.token ?: ""
            preferences[USER_IS_LOGIN] = user.isLogin
        }
    }

    fun getSession(): Flow<UserSession> {
        return dataStore.data.map { preferences ->
            UserSession(
                preferences[USER_NAME] ?: "",
                preferences[USER_TOKEN] ?: "",
                preferences[USER_IS_LOGIN] ?: false
            )
        }
    }

    fun getUsername(): Flow<String> {
        return dataStore.data.map { preferences ->
            val name = preferences[USER_NAME] ?: ""
            Log.d("UserPreference", "Get Username: $name")
            name
        }
    }

    suspend fun saveLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE] = language
        }
    }

    fun getLanguageSetting(): Flow<String> {
        return dataStore.data.map { preferences ->
            val lang = preferences[LANGUAGE] ?: "id"
            lang
        }
    }

    fun getLanguageSync(): String {
        return runBlocking {
            dataStore.data.first()[LANGUAGE] ?: "id"
        }
    }

    suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = user.name.toString()
        }
    }

    suspend fun saveUsername(name: String) {
        try {
            dataStore.edit { preferences ->
                preferences[USER_NAME] = name
                Log.d("UserPreference", "Username saved: $name")
            }
        } catch (e: Exception) {
            // Log the error or handle it appropriately
            Log.e("UserPreference", "Failed to save username: ${e.message}")
            throw e // Re-throw the exception if needed
        }
    }

    fun getUser(): Flow<User> {
        return dataStore.data.map { preferences ->
            User(
                preferences[USER_NAME] ?: ""
            )
        }
    }

    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val USER_NAME = stringPreferencesKey("name")
        private val USER_TOKEN = stringPreferencesKey("token")
        private val USER_IS_LOGIN = booleanPreferencesKey("isLogin")
        private val LANGUAGE = stringPreferencesKey("language")
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")