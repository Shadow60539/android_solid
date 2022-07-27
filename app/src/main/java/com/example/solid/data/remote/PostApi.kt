package com.example.solid.data.remote

import com.example.solid.data.dto.PostDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PostApi {

    @GET("/posts")
    suspend fun getPosts(): Response<List<PostDto>>

    @POST("/posts")
    suspend fun createPost(@Body post: PostDto): Response<PostDto>
}