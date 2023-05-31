package com.johnny.fagundes.spaceexplorer.utils.sharedprefs

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

open class BaseSharedPreferences(private val sharedPreferences: SharedPreferences, private val gson: Gson) {
    protected inline fun <reified T> getData(sharedPreferences: SharedPreferences, gson: Gson, key: String): T? {
        val jsonString = sharedPreferences.getString(key, null)
        return if (jsonString != null) {
            gson.fromJson(jsonString, object : TypeToken<T>() {}.type)
        } else {
            null
        }
    }

    protected fun <T> saveData(key: String, value: T) {
        val jsonString = gson.toJson(value)
        sharedPreferences.edit().putString(key, jsonString).apply()
    }
}