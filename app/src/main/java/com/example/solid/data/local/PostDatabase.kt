package com.example.solid.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.solid.data.dto.PostDto

@Database(entities = [PostDto::class], version = 1)
abstract class PostDatabase : RoomDatabase() {
    abstract fun dao(): PostDao
}