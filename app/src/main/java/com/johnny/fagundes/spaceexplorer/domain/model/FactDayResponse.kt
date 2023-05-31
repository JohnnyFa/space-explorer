package com.johnny.fagundes.spaceexplorer.domain.model

import java.time.LocalDate

data class FactDayResponse(
    val date: String,
    val explanation: String,
    val media_type: String,
    val title: String,
    val url: String
) {
    fun isToday(): Boolean {
        val today = LocalDate.now()
        val pictureDate = LocalDate.parse(date)
        return pictureDate.isEqual(today)
    }

    fun mediaType(): String{
        return media_type
    }

    companion object {
        const val IMAGE = "image"
        const val VIDEO = "video"
        const val AUDIO = "audio"
        const val Gif = "gif"
        const val Data = "data"
    }
}