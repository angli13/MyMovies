package com.fangli.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper

@Database(version = 1, entities = [Movie::class], exportSchema = false)
abstract class MoviesDatabase: RoomDatabase() {

    companion object {
        private var INSTANCE: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase? {
            if (INSTANCE == null) {
                synchronized(MoviesDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MoviesDatabase::class.java, "movies.db")
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }


    abstract fun moviesDao(): MoviesDao
}