package ru.droidcat.starline.feature.carsonmap.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.droidcat.starline.feature.carsonmap.model.VehicleGroup

@Suppress("DEPRECATION")
@Composable
fun CarsList(
    onVehicleSelected: (title: String, lat: Double, lon: Double) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CarsListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()

    Surface(
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(
                isRefreshing = state.value.loading
            ),
            onRefresh = {
                viewModel.getDevices()
            }
        ) {
            LazyColumn(
                modifier = modifier,
                contentPadding = WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom)
                    .asPaddingValues()
            ) {
                if (state.value.loading) {
                    loading()
                } else {
                    loadedCarsList(
                        carGroups = state.value.groups,
                        onCarSelected = onVehicleSelected
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.loadedCarsList(
    carGroups: List<VehicleGroup>,
    onCarSelected: (title: String, lat: Double, lon: Double) -> Unit
) {
    carGroups.forEach { group ->
        stickyHeader {
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(WindowInsetsSides.Start)
                        )
                        .padding(
                            vertical = CarsListDefaults.ITEM_VERTICAL_PADDING,
                            horizontal = CarsListDefaults.ITEM_HORIZONTAL_PADDING
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = group.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        items(group.vehicles) { vehicle ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onCarSelected(
                            vehicle.title,
                            vehicle.lat,
                            vehicle.lon
                        )
                    }
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
                    )
                    .padding(
                        vertical = CarsListDefaults.ITEM_VERTICAL_PADDING,
                        horizontal = CarsListDefaults.ITEM_HORIZONTAL_PADDING
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = vehicle.title,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Divider()
        }
    }
}

fun LazyListScope.loading() {
    items(CarsListDefaults.PLACEHOLDER_ITEMS_COUNT) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
                )
                .padding(
                    vertical = CarsListDefaults.ITEM_VERTICAL_PADDING,
                    horizontal = CarsListDefaults.ITEM_HORIZONTAL_PADDING
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .placeholder(
                        visible = true,
                        color = MaterialTheme.colorScheme.outlineVariant
                            .copy(alpha = CarsListDefaults.PLACEHOLDER_ALPHA),
                        shape = MaterialTheme.shapes.extraSmall,
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = MaterialTheme.colorScheme.surface
                        )
                    ),
                text = String(),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

private object CarsListDefaults {
    const val PLACEHOLDER_ITEMS_COUNT = 20
    const val PLACEHOLDER_ALPHA = 0.2f

    val ITEM_VERTICAL_PADDING = 8.dp
    val ITEM_HORIZONTAL_PADDING = 16.dp
}
