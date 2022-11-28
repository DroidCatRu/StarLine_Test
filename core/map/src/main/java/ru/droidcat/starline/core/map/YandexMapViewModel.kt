package ru.droidcat.starline.core.map

import androidx.lifecycle.ViewModel
import com.yandex.mapkit.MapKit
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YandexMapViewModel @Inject constructor(
    private val mapKit: MapKit
) : ViewModel() {

    fun startMapKit() {
        mapKit.onStart()
    }

    fun stopMapKit() {
        mapKit.onStop()
    }
}
