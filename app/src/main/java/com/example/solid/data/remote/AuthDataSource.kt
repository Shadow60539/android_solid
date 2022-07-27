package com.example.solid.data.remote

import com.example.solid.data.exception.AuthException
import kotlinx.coroutines.delay

interface AuthDataSource {
    suspend fun loginIn(): String
    suspend fun createAccount()
    suspend fun loginOut()
}

class AuthDataSourceImpl : AuthDataSource {
    override suspend fun loginIn(): String {
        delay(1000L)
        try {
            return "{\"_id\": 007,\"name\": \"James Bond\"}"
        } catch (e: AuthException) {
            when (e) {
                is AuthException.InvalidUsernameOrPassword -> TODO()
                is AuthException.ServerException -> TODO()
            }
        }
    }

    override suspend fun createAccount() {
        TODO("Not yet implemented")
    }

    override suspend fun loginOut() {
        TODO("Not yet implemented")
    }

}