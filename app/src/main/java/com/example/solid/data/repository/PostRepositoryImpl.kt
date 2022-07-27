package com.example.solid.data.repository

import com.example.solid.data.dto.PostDto
import com.example.solid.data.remote.PostDataSource
import com.example.solid.domain.model.Post
import com.example.solid.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val dataSource: PostDataSource) :
    PostRepository {

    override suspend fun getPosts(): List<Post> {
        val postsDtoList = dataSource.getPosts()
        // TODO: Cache local data source
        return postsDtoList.map { it.toPost() }
    }

    override suspend fun createPost(post: Post): Post {
        val newPostDto = dataSource.createPost(PostDto.fromPost(post))
        return newPostDto.toPost()
    }

}