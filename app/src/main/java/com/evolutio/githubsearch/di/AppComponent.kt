package com.evolutio.githubsearch.di

import android.app.Application
import com.evolutio.data.di.NetworkModule
import com.evolutio.data.di.RepositoryModule
import com.evolutio.data.di.ServiceModule
import com.evolutio.domain.di.DispatcherModule
import com.evolutio.githubsearch.GithubSearchApplication
import com.evolutio.presentation.di.ActivityModule
import com.evolutio.presentation.di.FragmentModule
import com.evolutio.presentation.di.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        DispatcherModule::class,
        RepositoryModule::class,
        ServiceModule::class,
        ActivityModule::class,
        FragmentModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(githubSearchApplication: GithubSearchApplication)
}