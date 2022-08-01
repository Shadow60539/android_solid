package com.example.solid.data.exception

sealed class PostException : Exception() {
    object ServerException : PostException()
    object ClientException : PostException()
    object CreatePostException : PostException()
    object CacheException : PostException()
}