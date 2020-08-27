package com.breaking.bad.di

import com.breaking.bad.business.data.network.NetworkDataSource
import com.breaking.bad.business.interactors.GetCharacters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object InteractorsModule {

    @Singleton
    @Provides
    fun provideGetCharactersInteractor(
        networkDataSource: NetworkDataSource
    ): GetCharacters {
        return GetCharacters(networkDataSource)
    }
}