package com.example.solid.domain.repository

import com.example.solid.domain.model.User

interface AuthRepository {
    suspend fun loginIn(): User
    suspend fun createAccount()
    suspend fun loginOut()
}