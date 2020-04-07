package com.evolutio.githubsearch

import android.app.Application
import com.evolutio.githubsearch.di.AppComponent
import com.evolutio.githubsearch.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class GithubSearchApplication : Application(), HasAndroidInjector {

    private lateinit var appComponent: AppComponent

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()

        initializeAppComponent()
    }

    private fun initializeAppComponent() {
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
    }
}