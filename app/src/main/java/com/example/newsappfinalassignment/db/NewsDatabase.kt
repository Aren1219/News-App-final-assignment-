package com.example.newsappfinalassignment.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsappfinalassignment.model.Data

@Database(
    entities = [Data::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun getDao(): NewsDao
}