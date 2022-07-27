package com.example.solid.data.dto

import com.example.solid.domain.model.User
import com.squareup.moshi.Json

data class UserDto(@Json(name = "_id") val id: Int, val name: String) {
    fun toUser(): User {
        return User(id, name)
    }
}