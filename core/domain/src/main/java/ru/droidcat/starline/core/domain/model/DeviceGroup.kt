package ru.droidcat.starline.core.domain.model

data class DeviceGroup(
    val title: String,
    val name: String,
    val devices: List<Device>
)
