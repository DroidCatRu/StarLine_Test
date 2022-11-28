package ru.droidcat.starline.core.network.model

import kotlinx.serialization.Serializable

@Serializable
internal data class JsonResponse(
    val devices: Devices? = null
)
