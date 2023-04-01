package com.buffersolve.cuton.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buffersolve.cuton.core.domain.SessionManager
import com.buffersolve.cuton.core.util.onResult
import com.buffersolve.cuton.feature.home.data.repository.HomeRepository
import com.buffersolve.cuton.feature.home.ui.state.ItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val repository: HomeRepository
): ViewModel() {

//    private val _itemState = MutableSharedFlow<ItemState>(replay = 1)
//    val itemState: SharedFlow<ItemState> = _itemState.asSharedFlow()

    private val _itemState = MutableSharedFlow<ItemState>(replay = 1)
    val itemState: SharedFlow<ItemState> = _itemState.asSharedFlow()

    fun getToken(): String? {
        return sessionManager.getUserTokenOrNull()
    }

    fun getUserInfo() = viewModelScope.launch(Dispatchers.IO) { repository.getUserInfo() }

    fun getHomeMenuItems() = viewModelScope.launch(Dispatchers.IO) {

        // iInit State
        _itemState.emit(ItemState.Loading)

        repository.getHomeMenuItems().onResult(
            onSuccess = {
                // TODO IT
                it.success?.let { it1 -> ItemState.Success(it1) }
                    ?.let { it2 -> _itemState.emit(it2) }
            },
            onFailure = {
                _itemState.emit(ItemState.Error(it.cause.message.toString()))

            }
        )

    }

}