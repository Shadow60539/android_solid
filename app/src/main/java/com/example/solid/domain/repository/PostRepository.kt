package com.example.solid.domain.repository

import arrow.core.Either
import com.example.solid.domain.failure.PostFailure
import com.example.solid.domain.model.Post

interface PostRepository {
    suspend fun getPosts(): Either<PostFailure, List<Post>>
    suspend fun createPost(post: Post): Either<PostFailure, Post>
}