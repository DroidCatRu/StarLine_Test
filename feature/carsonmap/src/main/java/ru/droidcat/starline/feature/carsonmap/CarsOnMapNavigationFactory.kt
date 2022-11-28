package ru.droidcat.starline.feature.carsonmap

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.droidcat.starline.core.navigation.FeatureIntentManager
import ru.droidcat.starline.core.navigation.NavigateIntent
import ru.droidcat.starline.core.navigation.NavigationFactory
import ru.droidcat.starline.feature.carsonmap.presentation.CarsList
import ru.droidcat.starline.feature.carsonmap.presentation.MapScreen
import javax.inject.Inject

const val CARS_ON_MAP_FEATURE_ROUTE = "CarsOnMapFeatureRoute"

internal const val MAIN_SCREEN_ROUTE = "CarsOnMapMain"
internal const val MAP_SCREEN_ROUTE = "CarsOnMapMapScreen"

internal class CarsOnMapNavigationFactory @Inject constructor(
    private val intentManager: FeatureIntentManager
) : NavigationFactory {

    override fun create(builder: NavGraphBuilder) {
        builder.navigation(
            route = CARS_ON_MAP_FEATURE_ROUTE,
            startDestination = MAIN_SCREEN_ROUTE
        ) {
            composable(route = MAIN_SCREEN_ROUTE) {
                CarsList(
                    modifier = Modifier.fillMaxSize(),
                    onVehicleSelected = { title, lat, lon ->
                        intentManager.sendIntent(
                            NavigateIntent(
                                route = "$MAP_SCREEN_ROUTE?title=$title&lat=$lat&lon=$lon"
                            )
                        )
                    }
                )
            }

            composable(
                route = "$MAP_SCREEN_ROUTE?title={title}&lat={lat}&lon={lon}",
                arguments = listOf(
                    navArgument("title") {
                        type = NavType.StringType
                        nullable = true
                    },
                    navArgument("lat") {
                        type = NavType.StringType
                        nullable = true
                    },
                    navArgument("lon") {
                        type = NavType.StringType
                        nullable = true
                    }
                )
            ) {
                MapScreen(
                    modifier = Modifier.fillMaxSize(),
                    title = it.arguments?.getString("title"),
                    lat = it.arguments?.getString("lat"),
                    lon = it.arguments?.getString("lon")
                )
            }
        }
    }
}
