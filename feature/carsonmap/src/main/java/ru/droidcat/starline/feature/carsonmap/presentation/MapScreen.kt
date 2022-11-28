package ru.droidcat.starline.feature.carsonmap.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.droidcat.starline.core.map.YandexMap
import ru.droidcat.starline.core.map.model.MapCameraPosition
import ru.droidcat.starline.core.map.model.Placemark
import ru.droidcat.starline.feature.carsonmap.R

@Composable
fun MapScreen(
    title: String?,
    lat: String?,
    lon: String?,
    modifier: Modifier = Modifier
) {
    val latitude = try {
        lat?.toDouble()
    } catch (e: NumberFormatException) {
        null
    }

    val longitude = try {
        lon?.toDouble()
    } catch (e: NumberFormatException) {
        null
    }

    if (latitude == null || longitude == null) {
        NoVehicleSelected(modifier)
    } else {
        YandexMap(
            modifier = modifier,
            cameraPosition = MapCameraPosition(latitude, longitude),
            placemark = Placemark(
                title = title ?: stringResource(R.string.default_car_marker_text),
                lat = latitude,
                lon = longitude
            )

        )
    }
}

@Composable
fun NoVehicleSelected(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(R.string.no_vehicle_selected_error))
    }
}
