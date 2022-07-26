package com.example.solid.data.repository

import com.example.solid.data.dto.UserDto
import com.example.solid.data.remote.AuthDataSource
import com.example.solid.domain.model.User
import com.example.solid.domain.repository.AuthRepository

class AuthRepositoryImpl(private val dataSource: AuthDataSource) : AuthRepository {

    override suspend fun loginIn(): User {
        val json = dataSource.loginIn()
        return UserDto.fromJson(json)
    }

    override suspend fun createAccount() {
        TODO("Not yet implemented")
    }

    override suspend fun loginOut() {
        TODO("Not yet implemented")
    }
}