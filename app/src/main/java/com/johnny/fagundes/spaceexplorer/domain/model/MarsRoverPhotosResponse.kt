package com.johnny.fagundes.spaceexplorer.domain.model

data class MarsRoverPhotosResponse(
    val photos: List<MarsRoverPhoto>
)

data class MarsRoverPhoto(
    val id: Int,
    val sol: Int,
    val camera: Camera,
    val img_src: String,
    val earth_date: String,
    val rover: Rover
)

data class Camera(
    val id: Int,
    val name: String,
    val rover_id: Int,
    val full_name: String
)

data class Rover(
    val id: Int,
    val name: String,
    val landing_date: String,
    val launch_date: String,
    val status: String
)