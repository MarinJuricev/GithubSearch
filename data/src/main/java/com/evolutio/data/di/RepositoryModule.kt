package com.evolutio.data.di

import com.evolutio.data.remote.RemoteGithubRepositoryImpl
import com.evolutio.data.remote.RestApiInterface
import com.evolutio.domain.repository.IGithubRepository
import com.evolutio.domain.shared.DispatcherProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideGithubRepository(
        dispatcherProvider: DispatcherProvider,
        restApiInterface: RestApiInterface
    ): IGithubRepository = RemoteGithubRepositoryImpl(
        dispatcherProvider,
        restApiInterface
    )
}