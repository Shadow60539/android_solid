package com.example.solid.domain.usecase

import com.example.solid.domain.repository.AuthRepository

class LoginInUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke() {
        repository.loginIn()
    }
}