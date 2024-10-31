package com.poc.login.domain.mapper

import com.poc.login.data.responses.UserResponse
import com.poc.login.domain.model.User

interface LoginMapper {
    suspend fun toDomain(userResponse: UserResponse): User
}