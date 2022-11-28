package ru.droidcat.starline.feature.carsonmap.model

import ru.droidcat.starline.core.domain.model.Device

data class Vehicle(
    val type: String,
    val title: String,
    val lat: Double,
    val lon: Double
)

fun Device.toVehicle(): Vehicle {
    return Vehicle(
        type = this.type,
        title = this.title,
        lat = this.lat,
        lon = this.lon
    )
}
