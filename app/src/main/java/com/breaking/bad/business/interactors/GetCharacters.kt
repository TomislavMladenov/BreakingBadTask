package com.breaking.bad.business.interactors

import com.breaking.bad.business.data.network.NetworkDataSource
import com.breaking.bad.business.state.DataState
import com.breaking.bad.framework.datasource.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCharacters
constructor(
    private val networkDataSource: NetworkDataSource
){

    /**
     * Show loading
     * Get characters
     * Propagate to the UI
     */
    suspend fun execute(): Flow<DataState<List<Character>>> = flow {
        emit(DataState.Loading)
        val networkCall = networkDataSource.getCharacters()
        emit(DataState.Success(networkCall))
    }
}