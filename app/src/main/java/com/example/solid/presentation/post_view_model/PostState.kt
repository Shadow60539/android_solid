package com.example.solid.presentation.post_view_model

sealed class PostState<T>(val data: T? = null, val message: String? = null) {
    class Empty<T> : PostState<T>(data = null, message = null)
    class Loading<T> : PostState<T>(message = null)
    class Success<T>(data: T? = null) : PostState<T>(data, message = null)
    class Error<T>(msg: String) : PostState<T>(message = msg)
}
