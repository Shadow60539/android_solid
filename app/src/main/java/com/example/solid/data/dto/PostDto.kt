package com.example.solid.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.solid.domain.model.Post

@Entity(tableName = "post")
data class PostDto(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
) {
    fun toPost() = Post(id = id, userId = userId, title = title, body = body)

    companion object {
        fun fromPost(post: Post) =
            PostDto(id = post.id, userId = post.userId, title = post.title, body = post.body)
    }
}