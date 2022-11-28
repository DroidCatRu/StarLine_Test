package ru.droidcat.starline.core.map.model

import ru.droidcat.starline.core.map.YandexMapDefaults.DEFAULT_CAMERA_ZOOM

data class MapCameraPosition(
    val lat: Double,
    val lon: Double,
    val zoom: Float = DEFAULT_CAMERA_ZOOM
)
