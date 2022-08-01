package com.example.solid.presentation.post_view_model

import arrow.core.Either
import com.example.solid.domain.failure.PostFailure
import com.example.solid.domain.model.Post

sealed class PostEventListener {
    data class OnGetPosts(val failureOrSuccess: Either<PostFailure, MutableList<Post>>) :
        PostEventListener()

    data class OnAddPost(val failureOrSuccess: Either<PostFailure, Post>) : PostEventListener()
}