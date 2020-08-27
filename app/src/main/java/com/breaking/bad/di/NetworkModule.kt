package com.breaking.bad.di

import com.breaking.bad.business.data.network.NetworkDataSource
import com.breaking.bad.business.data.network.NetworkDataSourceImpl
import com.breaking.bad.business.data.utils.NetworkConstants.Companion.BASE_URL
import com.breaking.bad.framework.datasource.CharacterService
import com.breaking.bad.framework.datasource.CharacterServiceImpl
import com.breaking.bad.framework.datasource.api.CharactersApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideCharacterApi(retrofit: Retrofit.Builder): CharactersApi {
        return retrofit
            .build()
            .create(CharactersApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCharacterService(
        charactersApi: CharactersApi
    ): CharacterService {
        return CharacterServiceImpl(charactersApi)
    }

    @Singleton
    @Provides
    fun provideNetworkDataSource(
        characterService: CharacterService
    ): NetworkDataSource {
        return NetworkDataSourceImpl(characterService)
    }


}