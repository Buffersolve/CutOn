package com.buffersolve.cuton.feature.catalog.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buffersolve.cuton.core.util.onResult
import com.buffersolve.cuton.feature.catalog.data.remote.api.models.dto.CatalogDTO
import com.buffersolve.cuton.feature.catalog.data.remote.api.models.X10
import com.buffersolve.cuton.feature.catalog.data.repository.CatalogRepository
import com.buffersolve.cuton.feature.catalog.ui.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val repository: CatalogRepository
) : ViewModel() {

    private val _catalogState = MutableSharedFlow<HomeState>(replay = 1)
    val catalogState: SharedFlow<HomeState> = _catalogState.asSharedFlow()

    fun getCatalog() = viewModelScope.launch(Dispatchers.IO) {

        // Init State
        _catalogState.emit(HomeState.Loading)

        repository.getCatalog().onResult(
            onSuccess = { answer ->

                // Map DTO to Model
                val result = answer.success.brandsList?.toList()?.map {
                    mapDTOToModel(it.second)
                }

                if (!result.isNullOrEmpty()) {
                    _catalogState.emit(HomeState.Success(result))
                }
            },
            onFailure = {
                _catalogState.emit(HomeState.Error(it.cause.message.toString()))
            }
        )
    }


    private fun mapDTOToModel(dto: CatalogDTO): X10 {
        return X10(
            dto.brandId ?: 0,
            dto.brandImage ?: "",
            dto.brandName ?: ""
        )
    }

}