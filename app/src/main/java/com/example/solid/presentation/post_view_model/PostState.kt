package com.example.solid.presentation.post_view_model

import com.example.solid.domain.model.Post

data class PostState(
    val isLoading: Boolean = false,
    val posts: MutableList<Post> = mutableListOf(),
)
