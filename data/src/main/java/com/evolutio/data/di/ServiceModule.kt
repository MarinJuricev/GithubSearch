package com.evolutio.data.di

import android.app.Application
import com.evolutio.data.service.EncryptedPrefsServiceImpl
import com.evolutio.data.service.LoginServiceImpl
import com.evolutio.data.service.SharedPrefsServiceImpl
import com.evolutio.domain.service.IEncryptedPrefsService
import com.evolutio.domain.service.ILoginService
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

    @Provides
    @Singleton
    fun provideEncryptedPrefsService(githubApplication: Application): IEncryptedPrefsService =
        EncryptedPrefsServiceImpl(githubApplication)


    @Provides
    @Singleton
    fun provideLoginService(githubApplication: Application, encryptedPrefsService: IEncryptedPrefsService): ILoginService =
        LoginServiceImpl(githubApplication, encryptedPrefsService)


}

