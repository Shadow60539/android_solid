package com.example.solid.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.solid.data.dto.PostDto

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(vararg posts: PostDto)

    @Query("SELECT * FROM post")
    suspend fun getPosts(): List<PostDto>
}