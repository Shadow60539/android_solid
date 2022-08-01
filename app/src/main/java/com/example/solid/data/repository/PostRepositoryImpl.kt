package com.example.solid.data.repository

import arrow.core.Either
import com.example.solid.data.core.InternetConnection
import com.example.solid.data.dto.PostDto
import com.example.solid.data.exception.PostException
import com.example.solid.data.local.PostLocalDataSource
import com.example.solid.data.remote.PostRemoteDataSource
import com.example.solid.domain.failure.PostFailure
import com.example.solid.domain.model.Post
import com.example.solid.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val dataSource: PostRemoteDataSource,
    private val localDataSource: PostLocalDataSource,
    private val internetConnection: InternetConnection
) :
    PostRepository {

    override suspend fun getPosts(): Either<PostFailure, List<Post>> {
        return try {
            if (internetConnection.isConnected()) {
                val postsDtoList = dataSource.getPosts()
                localDataSource.cachePosts(postsDtoList)
                Either.Right(postsDtoList.map { it.toPost() })
            } else {
                Either.Right(localDataSource.getPosts().map { it.toPost() })
            }
        } catch (e: PostException) {
            when (e) {
                PostException.CreatePostException -> Either.Left(PostFailure.CreatePostFailure)
                PostException.ServerException -> Either.Left(PostFailure.ServerFailure)
                PostException.CacheException -> Either.Left(PostFailure.CacheFailure)
                PostException.ClientException -> Either.Left(PostFailure.ClientFailure)
            }
        }
    }

    override suspend fun createPost(post: Post): Either<PostFailure, Post> {
        return try {
            val newPostDto = dataSource.createPost(PostDto.fromPost(post))
            // Cache this post also?
            return Either.Right(newPostDto.toPost())
        } catch (e: PostException) {
            when (e) {
                PostException.CreatePostException -> Either.Left(PostFailure.CreatePostFailure)
                PostException.ServerException -> Either.Left(PostFailure.ServerFailure)
                PostException.CacheException -> Either.Left(PostFailure.CacheFailure)
                PostException.ClientException -> Either.Left(PostFailure.ClientFailure)
            }
        }
    }

}