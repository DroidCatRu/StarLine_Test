package ru.droidcat.starline.core.domain.usecase

import ru.droidcat.starline.core.domain.NetworkDataSource
import ru.droidcat.starline.core.domain.model.DeviceGroup
import javax.inject.Inject

class GetDevicesUseCase @Inject constructor(
    private val networkSource: NetworkDataSource
) {
    suspend operator fun invoke(): List<DeviceGroup> {
        // In the future we can add there some logic to save data in database, for example
        return networkSource.getDeviceGroups()
    }
}
