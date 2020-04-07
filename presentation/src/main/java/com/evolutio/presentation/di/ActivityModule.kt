package com.evolutio.presentation.di

import com.evolutio.presentation.BaseActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeBaseActivity(): BaseActivity
}