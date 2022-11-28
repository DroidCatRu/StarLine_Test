package ru.droidcat.starline.core.map

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import ru.droidcat.starline.core.map.model.MapCameraPosition
import ru.droidcat.starline.core.map.model.Placemark

@Composable
fun YandexMap(
    cameraPosition: MapCameraPosition,
    placemark: Placemark,
    modifier: Modifier = Modifier,
    darkMode: Boolean = isSystemInDarkTheme(),
    viewModel: YandexMapViewModel = hiltViewModel()
) {
    var mapView: MapView? = null

    DisposableEffect(Unit) {
        viewModel.startMapKit()
        mapView?.onStart()
        onDispose {
            mapView?.onStop()
            viewModel.stopMapKit()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            viewModel.startMapKit()
            MapView(ctx)
                .apply { onStart() }
                .also { map ->
                    mapView = map
                    map.map.isNightModeEnabled = darkMode
                    placemark.let {
                        map.map.mapObjects
                            .addPlacemark(
                                Point(it.lat, it.lon),
                                ImageProvider.fromResource(ctx, R.drawable.search_result),
                                IconStyle().setScale(YandexMapDefaults.DEFAULT_PLACEMARK_ICON_SCALE)
                            )
                            .setText(
                                it.title,
                                TextStyle()
                                    .setPlacement(TextStyle.Placement.TOP)
                                    .setSize(YandexMapDefaults.DEFAULT_PLACEMARK_TEXT_SIZE)
                            )
                    }
                    cameraPosition.let {
                        map.map.move(
                            CameraPosition(
                                Point(it.lat, it.lon),
                                it.zoom,
                                YandexMapDefaults.DEFAULT_AZIMUTH,
                                YandexMapDefaults.DEFAULT_TILT
                            ),
                            Animation(
                                Animation.Type.SMOOTH,
                                YandexMapDefaults.DEFAULT_ANIMATION_DURATION
                            ),
                            null
                        )
                    }
                }
        },
        update = {
            it.map.isNightModeEnabled = darkMode
        }
    )
}

object YandexMapDefaults {
    const val DEFAULT_ANIMATION_DURATION = 1.2f

    const val DEFAULT_AZIMUTH = 0f
    const val DEFAULT_TILT = 0f

    const val DEFAULT_PLACEMARK_ICON_SCALE = 0.5f
    const val DEFAULT_PLACEMARK_TEXT_SIZE = 10f

    const val DEFAULT_CAMERA_ZOOM = 17f
}
