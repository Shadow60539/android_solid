package com.example.solid.data.exception

sealed class AuthException : Exception() {
    class InvalidUsernameOrPassword : AuthException()
    class ServerException : AuthException()
}