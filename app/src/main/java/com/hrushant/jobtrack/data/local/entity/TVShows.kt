package com.hrushant.jobtrack.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_shows")
data class TVShow(

    @PrimaryKey
    val id: Int,

    val title: String,

    val description: String,

    val posterUrl: String,

    val genre: String,

    val rating: Double,

    val releaseYear: Int,

    val category: String
)