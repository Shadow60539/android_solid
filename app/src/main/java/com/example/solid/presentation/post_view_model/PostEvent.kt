package com.example.solid.presentation.post_view_model

sealed class PostEvent {
    object GetPosts : PostEvent()
    object ConfigurationChange : PostEvent()
    data class AddPost(val title: String, val body: String) : PostEvent()
}