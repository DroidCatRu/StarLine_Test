package ru.droidcat.starline.core.network.model

import kotlinx.serialization.Serializable
import ru.droidcat.starline.core.domain.model.Device

@Serializable
internal data class ListItem(
    val parent: String,
    val title: String,
    val group: String? = null,
    val type: String? = null,
    val lat: Double? = null,
    val lon: Double? = null
) {
    fun mapToDeviceOrNull(): Device? {
        return if (
            type.isNullOrEmpty() ||
            lat == null ||
            lon == null
        ) {
            null
        } else {
            Device(
                title = title,
                type = type,
                lat = lat,
                lon = lon
            )
        }
    }
}
