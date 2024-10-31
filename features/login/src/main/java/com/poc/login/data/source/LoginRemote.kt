package com.poc.login.data.source

import com.poc.data.result.OutCome
import com.poc.login.data.requests.LoginRequestBody
import com.poc.login.domain.model.User

interface LoginRemote {

    suspend fun login(loginRequestBody: LoginRequestBody): OutCome<User>
}