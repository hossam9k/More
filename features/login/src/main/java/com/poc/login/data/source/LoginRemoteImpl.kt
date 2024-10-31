package com.poc.login.data.source

import com.poc.data.error.toDomain
import com.poc.data.result.OutCome
import com.poc.data.source.NetworkDataSource
import com.poc.login.data.requests.LoginRequestBody
import com.poc.login.data.service.LoginService
import com.poc.login.domain.mapper.LoginMapper
import com.poc.login.domain.model.User

class LoginRemoteImpl(
    private val networkDataSource: NetworkDataSource<LoginService>,
    private val loginMapper: LoginMapper,
) :
    LoginRemote {

    override suspend fun login(loginRequestBody: LoginRequestBody): OutCome<User> {
        return networkDataSource.performRequest(
            request = {
                login(loginRequestBody).await()
            },
            onSuccess = { response, _ ->
                OutCome.success(loginMapper.toDomain(response))
            },
            onError = { errorResponse, code ->
                OutCome.error(errorResponse.toDomain(code))
            },
        )
    }
}