package com.example.solid.domain.usecase

import com.example.solid.domain.model.Post
import com.example.solid.domain.repository.PostRepository
import javax.inject.Inject

class CreatePost @Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke(post: Post): Post = repository.createPost(post)
}