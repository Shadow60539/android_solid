package com.example.solid.data.exception

sealed class PostException : Exception() {
    object ServerException : PostException()
    object CreatePostException : PostException()
}