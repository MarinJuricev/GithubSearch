package com.evolutio.domain.di

import com.evolutio.domain.feature.search.GetRepositories
import com.evolutio.domain.feature.search.PrepareRepositoryData
import com.evolutio.domain.feature.user_detail.GetUserData
import com.evolutio.domain.feature.user_detail.PrepareUserData
import com.evolutio.domain.repository.IGithubRepository
import com.evolutio.domain.shared.DispatcherProvider
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideGetRepositories(
        githubRepository: IGithubRepository
    ) = GetRepositories(githubRepository)

    @Provides
    fun providePrepareRepositoryData(
        dispatcherProvider: DispatcherProvider
    ) = PrepareRepositoryData(dispatcherProvider)

    @Provides
    fun provideGetUserData(
        githubRepository: IGithubRepository
    ) = GetUserData(githubRepository)

    @Provides
    fun providePrepareUserData(
        dispatcherProvider: DispatcherProvider
    ) = PrepareUserData(dispatcherProvider)
}