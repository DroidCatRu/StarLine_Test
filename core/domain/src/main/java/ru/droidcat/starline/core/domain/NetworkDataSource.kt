package ru.droidcat.starline.core.domain

import ru.droidcat.starline.core.domain.model.DeviceGroup

interface NetworkDataSource {
    suspend fun getDeviceGroups(): List<DeviceGroup>
}
