package com.evolutio.data.di

import android.app.Application
import com.evolutio.data.service.SharedPrefsServiceImpl
import com.evolutio.domain.service.ISharedPrefsService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServiceModule {
    @Provides
    @Singleton
    fun provideSharedPrefsService(githubApplication: Application): ISharedPrefsService =
        SharedPrefsServiceImpl(githubApplication)
}

