package com.poc.login.data.di

import com.google.gson.Gson
import com.poc.data.connectivity.NetworkMonitorInterface
import com.poc.data.constants.DISPATCHER_DEFAULT_TAG
import com.poc.data.constants.USER_ID_TAG
import com.poc.data.factory.ServiceFactory
import com.poc.data.source.NetworkDataSource
import com.poc.login.data.service.LoginService
import com.poc.login.data.source.LoginRemote
import com.poc.login.data.source.LoginRemoteImpl
import com.poc.login.domain.mapper.LoginMapper
import com.poc.login.domain.mapper.LoginMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideLoginServiceFactory(serviceFactory: ServiceFactory): LoginService {
        return serviceFactory.create(LoginService::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkDataSource(
        loginService: LoginService,
        gson: Gson,
        networkMonitorInterface: NetworkMonitorInterface,
        @Named(USER_ID_TAG) userIdProvider: () -> String,
    ): NetworkDataSource<LoginService> {
        return NetworkDataSource(
            loginService,
            gson,
            networkMonitorInterface,
            userIdProvider
        )
    }

    @Provides
    @Singleton
    fun provideLoginMapper(
        @Named(DISPATCHER_DEFAULT_TAG) coroutineDispatcher: CoroutineDispatcher,
    ): LoginMapper {
        return LoginMapperImpl(coroutineDispatcher)
    }

    @Provides
    @Singleton
    fun provideLoginRemoteImpl(
        networkDataSource: NetworkDataSource<LoginService>,
        loginMapper: LoginMapper,
    ): LoginRemote {
        return LoginRemoteImpl(networkDataSource, loginMapper)
    }
}