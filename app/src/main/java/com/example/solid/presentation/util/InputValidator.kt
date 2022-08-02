package com.example.solid.presentation.util

object InputValidator {
    fun isValidateTitle(title: String): Boolean {
        return title.trim().isNotBlank()
    }

    fun isValidateBody(body: String): Boolean {
        return body.trim().isNotBlank()
    }
}