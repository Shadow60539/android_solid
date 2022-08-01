package com.example.solid.domain.usecase

import arrow.core.Either
import com.example.solid.domain.failure.PostFailure
import com.example.solid.domain.model.Post
import com.example.solid.domain.repository.PostRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke(): Either<PostFailure, List<Post>> = repository.getPosts()
}