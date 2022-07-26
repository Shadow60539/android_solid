package com.example.solid.data.dto

import com.example.solid.domain.model.User

class UserDto private constructor(id: Int, name: String) {
    companion object {
        fun fromJson(json: Map<String, Any>): User {
            return User(json["_id"] as Int, json["name"] as String)
        }
    }
}