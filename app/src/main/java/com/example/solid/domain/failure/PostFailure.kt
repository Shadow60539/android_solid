package com.example.solid.domain.failure

sealed class PostFailure {
    object ServerFailure : PostFailure()
    object CreatePostFailure : PostFailure()
}
