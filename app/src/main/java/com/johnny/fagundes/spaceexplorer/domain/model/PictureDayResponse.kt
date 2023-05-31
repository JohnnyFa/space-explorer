package com.johnny.fagundes.spaceexplorer.domain.model

import java.time.LocalDate

data class PictureDayResponse(
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
}