package com.johnny.fagundes.spaceexplorer.feature.utils.sharedprefs

import android.content.SharedPreferences
import com.google.gson.Gson
import com.johnny.fagundes.spaceexplorer.domain.model.PictureDayResponse

class NasaSharedPreferences(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : BaseSharedPreferences(sharedPreferences, gson) {

    fun getSavedPicture(): PictureDayResponse? {
        return getData(sharedPreferences, gson, PICTURE)
    }

    fun savePicture(picture: PictureDayResponse) {
        saveData(PICTURE, picture)
    }

    companion object {
        const val PICTURE = "pictureData"
    }

}