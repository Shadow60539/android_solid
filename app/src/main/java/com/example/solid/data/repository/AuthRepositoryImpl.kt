package com.example.solid.data.repository

import com.example.solid.data.dto.UserDto
import com.example.solid.data.remote.AuthDataSource
import com.example.solid.domain.model.User
import com.example.solid.domain.repository.AuthRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val dataSource: AuthDataSource) :
    AuthRepository {

    override suspend fun loginIn(): User {
        val json = dataSource.loginIn()
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(UserDto::class.java)
        return adapter.fromJsonValue(json)!!.toUser()
    }

    override suspend fun createAccount() {
        TODO("Not yet implemented")
    }

    override suspend fun loginOut() {
        TODO("Not yet implemented")
    }
}