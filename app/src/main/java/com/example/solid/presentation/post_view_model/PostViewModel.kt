package com.example.solid.presentation.post_view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.example.solid.domain.model.Post
import com.example.solid.domain.usecase.CreatePostUseCase
import com.example.solid.domain.usecase.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    val getPostsUseCase: GetPostsUseCase,
    val createPostUseCase: CreatePostUseCase,
) : ViewModel() {

    var state by mutableStateOf<PostState>(PostState())
        private set

    private val _listener = Channel<PostEventListener>()
    val listener = _listener.receiveAsFlow()

    fun add(event: PostEvent) = when (event) {
        is PostEvent.GetPosts -> getPosts()
        is PostEvent.AddPost -> addPost(event.post)
    }

    private fun getPosts() {

        state = state.copy(isLoading = true)

        viewModelScope.launch {
            getPostsUseCase()
                .fold(ifLeft = { postFailure ->
                    _listener.send(PostEventListener.OnGetPosts(Either.Left(postFailure)))
                }, ifRight = { newPosts ->
                    state = state.copy(posts = newPosts.toMutableList())
                    _listener.send(PostEventListener.OnGetPosts(Either.Right(state.posts)))
                })

            state = state.copy(isLoading = false)
        }
    }

    private fun addPost(post: Post) {

        state = state.copy(isLoading = true)

        viewModelScope.launch {
            createPostUseCase(post).fold(
                ifLeft = { postFailure ->
                    _listener.send(PostEventListener.OnAddPost(Either.Left(postFailure)))
                },
                ifRight = { newPost ->
                    state.posts.add(0, newPost)
                    _listener.send(PostEventListener.OnAddPost(Either.Right(newPost)))
                })

            state = state.copy(isLoading = false)
        }
    }
}