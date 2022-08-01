package com.example.solid.presentation.post_view_model

import com.example.solid.domain.model.Post

sealed class PostEvent {
    object GetPosts : PostEvent()
    data class AddPost(val post: Post) : PostEvent()
}