package com.example.solid.data.repository

import arrow.core.Either
import com.example.solid.data.dto.PostDto
import com.example.solid.data.exception.PostException
import com.example.solid.data.remote.PostDataSource
import com.example.solid.domain.failure.PostFailure
import com.example.solid.domain.model.Post
import com.example.solid.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val dataSource: PostDataSource) :
    PostRepository {

    override suspend fun getPosts(): Either<PostFailure, List<Post>> {
        return try {
            val postsDtoList = dataSource.getPosts()
            // TODO: Cache local data source
            return Either.Right(postsDtoList.map { it.toPost() })
        } catch (e: PostException) {
            when (e) {
                PostException.CreatePostException -> Either.Left(PostFailure.CreatePostFailure)
                PostException.ServerException -> Either.Left(PostFailure.ServerFailure)
            }
        }
    }

    override suspend fun createPost(post: Post): Either<PostFailure, Post> {
        return try {
            val newPostDto = dataSource.createPost(PostDto.fromPost(post))
            return Either.Right(newPostDto.toPost())
        } catch (e: PostException) {
            when (e) {
                PostException.CreatePostException -> Either.Left(PostFailure.CreatePostFailure)
                PostException.ServerException -> Either.Left(PostFailure.ServerFailure)
            }
        }
    }

}