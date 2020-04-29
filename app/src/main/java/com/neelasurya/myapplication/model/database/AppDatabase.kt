package com.neelasurya.myapplication.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.neelasurya.myapplication.model.PostDao
import com.neelasurya.myapplication.model.Results

@Database(entities = [Results::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}