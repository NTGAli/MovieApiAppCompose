package com.ntg.movieapiappcompose.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val movieId: Long,
    val backdropPath: String? = null,
    val title: String,
)
