package com.mumtazfayyadh0102.iformula.network

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mumtazfayyadh0102.iformula.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_profile")

class UserDataStore private constructor(private val context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: UserDataStore? = null

        fun getInstance(context: Context): UserDataStore {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserDataStore(context.applicationContext).also {
                    INSTANCE = it
                }
            }
        }

        private val USER_NAME = stringPreferencesKey("name")
        private val USER_EMAIL = stringPreferencesKey("email")
        private val USER_PHOTO = stringPreferencesKey("photoUrl")
    }

    val userFlow: Flow<User> = context.dataStore.data.map { preferences ->
        User(
            name = preferences[USER_NAME] ?: "",
            email = preferences[USER_EMAIL] ?: "",
            photoUrl = preferences[USER_PHOTO] ?: ""
        )
    }

    suspend fun saveData(user: User) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = user.name
            preferences[USER_EMAIL] = user.email
            preferences[USER_PHOTO] = user.photoUrl
        }
    }
}
