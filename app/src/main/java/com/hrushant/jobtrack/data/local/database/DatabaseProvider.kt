package com.hrushant.jobtrack.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {

        return INSTANCE ?: synchronized(this) {

            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "netflix_database"
            )
                .addCallback(object : RoomDatabase.Callback() {

                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        // We'll insert initial movies here
                    }

                }).build()

            INSTANCE = instance

            instance
        }
    }
}