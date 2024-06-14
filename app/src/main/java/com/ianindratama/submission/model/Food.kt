package com.ianindratama.submission.model

data class Food(
    val id: Long,
    val name: String,
    val foodPictureUrl: String,
    val origin: String,
    val timeToMake: String,
    val description: String,
    val isFavorite: Boolean
)