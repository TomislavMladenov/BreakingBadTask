package com.breaking.bad.di

import android.content.Context
import com.breaking.bad.business.data.network.NetworkDataSource
import com.breaking.bad.framework.datasource.CharacterService
import com.breaking.bad.framework.datasource.FakeCharacterServiceImpl
import com.breaking.bad.framework.datasource.api.FakeCharacterApi
import com.breaking.bad.business.data.FakeNetworkDataSourceImpl
import com.breaking.bad.utils.JsonUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object TestNetworkModule {

    @Singleton
    @Provides
    fun provideJsonUtils(
        @ApplicationContext appContext: Context
    ): JsonUtil {
        return JsonUtil(appContext)
    }

    @Singleton
    @Provides
    fun provideFakeCharacterApi(
        jsonUtil: JsonUtil
    ): FakeCharacterApi {
        return FakeCharacterApi(jsonUtil)
    }

    @Singleton
    @Provides
    fun provideFakeCharacterService(
        fakeCharacterApi: FakeCharacterApi
    ): CharacterService {
        return FakeCharacterServiceImpl(fakeCharacterApi)
    }

    @Singleton
    @Provides
    fun provideFakeNetworkDataSource(
        characterService: CharacterService
    ): NetworkDataSource {
        return FakeNetworkDataSourceImpl(characterService)
    }
}