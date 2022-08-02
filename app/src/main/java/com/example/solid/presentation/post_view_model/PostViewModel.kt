package com.example.solid.presentation.post_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.solid.domain.failure.PostFailure
import com.example.solid.domain.model.Post
import com.example.solid.domain.usecase.CreatePostUseCase
import com.example.solid.domain.usecase.GetPostsUseCase
import com.example.solid.presentation.util.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    val getPostsUseCase: GetPostsUseCase,
    val createPostUseCase: CreatePostUseCase,
    val validator: InputValidator
) : ViewModel() {

    var getPostState = MutableStateFlow<PostState<MutableList<Post>>>(PostState.Empty())
        private set

    var addPostState = MutableStateFlow<PostState<Post>>(PostState.Empty())
        private set

    init {
        getPosts()
    }

    fun add(event: PostEvent) = when (event) {
        is PostEvent.GetPosts -> getPosts()
        is PostEvent.AddPost -> addPost(event.title, event.body)
        is PostEvent.ConfigurationChange -> onConfigurationChange()
    }

    private fun getPosts() {

        getPostState.value = PostState.Loading()

        viewModelScope.launch {
            getPostsUseCase()
                .fold(ifLeft = { postFailure ->
                    getPostState.value = PostState.Error(
                        when (postFailure) {
                            PostFailure.CacheFailure -> "No Cache Found"
                            PostFailure.ClientFailure -> "Client Failure"
                            PostFailure.ServerFailure -> "Server Failure"
                            else -> "null"
                        },
                    )
                }, ifRight = { newPosts ->
                    getPostState.value = PostState.Success(newPosts.toMutableList())
                })
        }
    }

    private fun addPost(title: String, body: String) {

        addPostState.value = PostState.Loading()

        if (!validator.isValidateTitle(title)) {
            addPostState.value = PostState.Error("Invalid Title")
            return
        }

        if (!validator.isValidateBody(body)) {
            addPostState.value = PostState.Error("Invalid Body")
            return
        }

        val post = Post(id = 1, userId = 1, title = title.trim(), body = body.trim())

        viewModelScope.launch {
            createPostUseCase(post).fold(
                ifLeft = { postFailure ->
                    addPostState.value = PostState.Error(
                        when (postFailure) {
                            PostFailure.CacheFailure -> "No Cache Found"
                            PostFailure.ClientFailure -> "Client Failure"
                            PostFailure.ServerFailure -> "Server Failure"
                            else -> "null"
                        },
                    )
                },
                ifRight = { newPost ->
                    getPostState.value.data?.add(0, newPost)
                    addPostState.value = PostState.Success(newPost)
                })

        }
    }

    private fun onConfigurationChange() {
        addPostState.value = PostState.Empty()
    }
}