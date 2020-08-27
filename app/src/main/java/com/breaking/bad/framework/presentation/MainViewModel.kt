package com.breaking.bad.framework.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breaking.bad.business.interactors.GetCharacters
import com.breaking.bad.business.state.DataState
import com.breaking.bad.framework.datasource.model.Character
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
class MainViewModel
@ViewModelInject
constructor(
    private val getCharacters: GetCharacters
): ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<Character>>> = MutableLiveData()

    val getDataState: LiveData<DataState<List<Character>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent){
        viewModelScope.launch {
            when(mainStateEvent){
                is MainStateEvent.GetCharactersEvent -> {
                    getCharacters.execute()
                        .onEach {dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }

}


sealed class MainStateEvent{

    object GetCharactersEvent: MainStateEvent()

    object None: MainStateEvent()
}
