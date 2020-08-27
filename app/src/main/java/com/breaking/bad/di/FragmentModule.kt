package com.breaking.bad.di

import com.breaking.bad.framework.presentation.util.SortUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(ApplicationComponent::class)
object FragmentModule {

    @Singleton
    @Provides
    fun provideSortUtils(): SortUtils {
        return SortUtils()
    }
}





