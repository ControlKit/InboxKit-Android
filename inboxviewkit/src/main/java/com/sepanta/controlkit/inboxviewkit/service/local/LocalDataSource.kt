package com.sepanta.controlkit.inboxviewkit.service.local
/*
 *  File: LocalDataSource.kt
 *
 *  Created by morteza on 9/22/25.
 */

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore("user_prefs")

class LocalDataSource(private val context: Context) {

    private val ID_LIST = stringSetPreferencesKey("id_list")

    suspend fun addId(id: String) {
        context.dataStore.edit { prefs ->
            val currentList = prefs[ID_LIST] ?: emptySet()
            prefs[ID_LIST] = currentList + id
        }
    }

    suspend fun getAllIds(): List<String> {
        val prefs = context.dataStore.data.first()
        return prefs[ID_LIST]?.toList() ?: emptyList()
    }

    suspend fun removeId(id: String) {
        context.dataStore.edit { prefs ->
            val currentList = prefs[ID_LIST] ?: emptySet()
            prefs[ID_LIST] = currentList - id
        }
    }

    suspend fun clearIds() {
        context.dataStore.edit { prefs ->
            prefs.remove(ID_LIST)
        }
    }
}
