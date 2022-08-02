package com.example.solid.data.remote

import android.util.Log
import com.example.solid.data.dto.PostDto
import com.example.solid.data.exception.PostException
import javax.inject.Inject

interface PostRemoteDataSource {
    suspend fun getPosts(): List<PostDto>
    suspend fun createPost(post: PostDto): PostDto
}

class PostRemoteDataSourceImpl @Inject constructor(private val api: PostApi) :
    PostRemoteDataSource {
    override suspend fun getPosts(): List<PostDto> {
        try {
            val response = api.getPosts()
            if (response.isSuccessful) {
                return response.body()!!
            }
        } catch (e: Exception) {
            throw PostException.ClientException
        }


        throw PostException.ServerException
    }

    override suspend fun createPost(post: PostDto): PostDto {
        try {
            val response = api.createPost(post)

            if (response.isSuccessful) {
                return response.body()!!
            }
        } catch (e: Exception) {
            throw PostException.ClientException
        }

        throw PostException.CreatePostException
    }


}