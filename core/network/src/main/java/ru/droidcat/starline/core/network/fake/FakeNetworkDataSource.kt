package ru.droidcat.starline.core.network.fake

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import ru.droidcat.starline.core.domain.NetworkDataSource
import ru.droidcat.starline.core.domain.model.Device
import ru.droidcat.starline.core.domain.model.DeviceGroup
import ru.droidcat.starline.core.network.model.JsonResponse
import ru.droidcat.starline.core.network.model.ListItem
import javax.inject.Inject
import kotlin.random.Random

internal class FakeNetworkDataSource @Inject constructor(
    private val networkJson: Json,
    private val assets: FakeAssetManager
) : NetworkDataSource {

    companion object {
        private const val DEVICES_ASSET = "devices.json"
        private const val MINIMUM_NETWORK_DELAY = 300
        private const val MAXIMUM_NETWORK_DELAY = 4000
    }

    @OptIn(ExperimentalSerializationApi::class)
    private suspend fun getJsonResponse(): JsonResponse =
        withContext(Dispatchers.IO) {
            assets.open(DEVICES_ASSET).use(networkJson::decodeFromStream)
        }

    override suspend fun getDeviceGroups(): List<DeviceGroup> {
        // Random delay between 300ms and 4s to simulate real network response
        val a = Random.nextInt(MINIMUM_NETWORK_DELAY, MAXIMUM_NETWORK_DELAY)
        delay(a.toLong())
        return getJsonResponse().devices?.list.parseGroups()
    }

    private fun List<ListItem>?.parseGroups(): List<DeviceGroup> {
        return this?.mapNotNull { group ->
            if (!group.group.isNullOrEmpty()) {
                val groupDevices: List<Device> = this
                    .mapNotNull { device ->
                        if (device.parent == group.group) {
                            device.mapToDeviceOrNull()
                        } else {
                            null
                        }
                    }
                DeviceGroup(
                    title = group.title,
                    name = group.group,
                    devices = groupDevices
                )
            } else {
                null
            }
        } ?: listOf()
    }
}
