package com.example.solid.data.dto

import com.example.solid.domain.model.Post

data class PostDto(
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