package com.evolutio.presentation.di

import com.evolutio.presentation.feature.login.LoginFragment
import com.evolutio.presentation.feature.private_user.PrivateUserDataFragment
import com.evolutio.presentation.feature.repository_detail.RepositoryDetailFragment
import com.evolutio.presentation.feature.search.SearchFragment
import com.evolutio.presentation.feature.user_detail.UserDetailFragment
import com.evolutio.presentation.shared.views.SortDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Scope

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributeUserDetailsFragment(): UserDetailFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributeRepositoryDetailFragment(): RepositoryDetailFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributeSortDialogFragment(): SortDialogFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributePrivateUserDataFragment(): PrivateUserDataFragment
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class FragmentScope