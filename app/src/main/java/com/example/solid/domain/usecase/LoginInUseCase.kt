package com.example.solid.domain.usecase

import com.example.solid.domain.model.User
import com.example.solid.domain.repository.AuthRepository

class LoginInUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): User {
        return repository.loginIn()
    }
}