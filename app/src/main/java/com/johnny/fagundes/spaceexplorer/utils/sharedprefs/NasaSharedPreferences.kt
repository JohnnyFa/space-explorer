package com.johnny.fagundes.spaceexplorer.utils.sharedprefs

import android.content.SharedPreferences
import com.google.gson.Gson
import com.johnny.fagundes.spaceexplorer.domain.model.FactDayResponse

class NasaSharedPreferences(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : BaseSharedPreferences(sharedPreferences, gson) {

    fun getSavedPicture(): FactDayResponse? {
        return getData(sharedPreferences, gson, PICTURE)
    }

    fun savePicture(picture: FactDayResponse) {
        saveData(PICTURE, picture)
    }

    companion object {
        const val PICTURE = "pictureData"
    }

}