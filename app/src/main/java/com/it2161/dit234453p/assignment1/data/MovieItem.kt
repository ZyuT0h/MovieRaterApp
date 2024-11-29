package com.it2161.dit234453p.assignment1.data


data class MovieItem(
    val title: String,
    val director: String,
    val releaseDate: String,
    val ratings_score: Float,
    val actors: List<String>,
    val image: String,
    val genre: String,
    val length: Int,
    val synopsis: String,
    val comment: MutableList<Comments> = mutableListOf()
)

data class Comments(
    val user: String,
    val comment: String,
    val date: String,
    val time: String,
)


