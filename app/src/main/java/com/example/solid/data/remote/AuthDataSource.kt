package com.example.solid.data.remote

import com.example.solid.data.exception.AuthException

interface AuthDataSource {
    suspend fun loginIn(): Map<String, Any>
    suspend fun createAccount()
    suspend fun loginOut()
}

class AuthDataSourceImpl : AuthDataSource {
    override suspend fun loginIn(): Map<String, Any> {
        try {
            return mapOf<String, Any>("_id" to 1, "name" to "James Bond")
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