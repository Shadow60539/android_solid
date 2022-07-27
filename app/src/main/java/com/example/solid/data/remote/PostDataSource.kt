package com.example.solid.data.remote

import com.example.solid.data.dto.PostDto
import com.example.solid.data.exception.PostException
import javax.inject.Inject

interface PostDataSource {
    suspend fun getPosts(): List<PostDto>
    suspend fun createPost(post: PostDto): PostDto
}

class PostDataSourceImpl @Inject constructor(private val api: PostApi) : PostDataSource {
    override suspend fun getPosts(): List<PostDto> {
        val response = api.getPosts()

        if (response.isSuccessful) {
            return response.body()!!
        }

        throw PostException.ServerException
    }

    override suspend fun createPost(post: PostDto): PostDto {
        val response = api.createPost(post)

        if (response.isSuccessful) {
            return response.body()!!
        }

        throw PostException.CreatePostException
    }


}