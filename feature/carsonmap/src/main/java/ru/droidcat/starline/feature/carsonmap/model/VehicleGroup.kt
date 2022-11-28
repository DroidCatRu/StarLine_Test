package ru.droidcat.starline.feature.carsonmap.model

import ru.droidcat.starline.core.domain.model.DeviceGroup

data class VehicleGroup(
    val groupName: String,
    val title: String,
    val vehicles: List<Vehicle>
)

fun DeviceGroup.toVehicleGroup(): VehicleGroup {
    return VehicleGroup(
        groupName = this.name,
        title = this.title,
        vehicles = this.devices.map { it.toVehicle() }
    )
}
