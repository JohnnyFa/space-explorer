package com.johnny.fagundes.spaceexplorer.domain.model

data class PictureDayResponse(
    val date: String,
    val explanation: String,
    val media_type: String,
    val title: String,
    val url: String
)