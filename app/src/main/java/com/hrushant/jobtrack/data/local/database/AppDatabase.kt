package com.hrushant.jobtrack.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hrushant.jobtrack.data.local.dao.MovieDao
import com.hrushant.jobtrack.data.local.dao.TVShowDao
import com.hrushant.jobtrack.data.local.entity.Movie
import com.hrushant.jobtrack.data.local.entity.TVShow
import kotlin.jvm.java


@Database(
    entities = [Movie::class,
        TVShow::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TVShowDao
}