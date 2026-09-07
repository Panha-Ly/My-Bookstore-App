package com.lypanha.mybookstore.model

data class Book(
    val id: Int,
    val seriesId: String,
    val name: String,
    val author: String,
    val publicationDate: String,
    val description: String,
    val price: String,
    val imageId: Int
)
