package com.example.solid.domain.repository

import com.example.solid.domain.model.Post

interface PostRepository {
    suspend fun getPosts(): List<Post>
    suspend fun createPost(post: Post): Post
}