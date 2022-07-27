package com.example.solid.data.dto

import com.example.solid.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserDto(@SerializedName("_id") val id: Int, val name: String) {
    fun toUser(): User {
        return User(id, name)
    }
}