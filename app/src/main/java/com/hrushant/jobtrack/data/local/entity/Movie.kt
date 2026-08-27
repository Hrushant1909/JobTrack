package com.hrushant.jobtrack.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    val id: Int,

    val title: String,

    val description: String,

    val posterUrl: String,

    val genre: String,

    val rating: Double,

    val releaseYear: Int,

    val category: String,
    val isInMyList: Boolean = false
)