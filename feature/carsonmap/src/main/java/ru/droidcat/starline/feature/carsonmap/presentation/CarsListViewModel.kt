package ru.droidcat.starline.feature.carsonmap.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.droidcat.starline.core.domain.usecase.GetDevicesUseCase
import ru.droidcat.starline.feature.carsonmap.model.VehicleGroup
import ru.droidcat.starline.feature.carsonmap.model.toVehicleGroup
import javax.inject.Inject

@HiltViewModel
class CarsListViewModel @Inject constructor(
    private val getDevicesUseCase: GetDevicesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CarsListState())
    val state = _state.asStateFlow()

    private var getDevicesJob: Job? = null

    init {
        getDevices()
    }

    fun getDevices() {
        _state.value = _state.value.copy(loading = true)

        getDevicesJob?.cancel()
        getDevicesJob = viewModelScope.launch {
            _state.value = _state.value.copy(
                groups = getDevicesUseCase().map { it.toVehicleGroup() },
                loading = false
            )
        }
    }
}

data class CarsListState(
    val groups: List<VehicleGroup> = listOf(),
    val loading: Boolean = false
)
