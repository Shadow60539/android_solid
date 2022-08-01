package com.example.solid.domain.failure

sealed class PostFailure {
    object ServerFailure : PostFailure()
    object ClientFailure : PostFailure()
    object CreatePostFailure : PostFailure()
    object CacheFailure : PostFailure()
}
