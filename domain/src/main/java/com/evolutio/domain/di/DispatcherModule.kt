package com.evolutio.domain.di

import com.evolutio.domain.shared.DispatcherProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DispatcherModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider() = DispatcherProvider

}