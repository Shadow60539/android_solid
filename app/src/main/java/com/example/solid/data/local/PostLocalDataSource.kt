package com.example.solid.data.local

import com.example.solid.data.dto.PostDto
import com.example.solid.data.exception.PostException
import javax.inject.Inject

interface PostLocalDataSource {
    suspend fun getPosts(): List<PostDto>
    suspend fun cachePosts(post: List<PostDto>)
}

class PostLocalDataSourceImpl @Inject constructor(private val dao: PostDao) :
    PostLocalDataSource {
    override suspend fun getPosts(): List<PostDto> {
        try {
            return dao.getPosts()
        } catch (e: Exception) {
            throw PostException.CacheException
        }
    }

    override suspend fun cachePosts(post: List<PostDto>) {
        try {
            dao.insertPosts(*post.toTypedArray())
        } catch (e: Exception) {
            throw PostException.CacheException
        }
    }

}