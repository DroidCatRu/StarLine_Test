package ru.droidcat.starline.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.coroutines.flow.StateFlow
import ru.droidcat.starline.R
import ru.droidcat.starline.core.navigation.FeatureIntentManager
import ru.droidcat.starline.core.navigation.NavigateIntent
import ru.droidcat.starline.core.navigation.NavigationFactory
import ru.droidcat.starline.feature.carsonmap.CARS_ON_MAP_FEATURE_ROUTE
import ru.droidcat.starline.utils.collectWithLifecycle

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class,
    ExperimentalLifecycleComposeApi::class
)
@Composable
fun StarLineApp(
    isOffline: StateFlow<Boolean>,
    navigationFactories: ImmutableSet<NavigationFactory>,
    featureIntentManager: FeatureIntentManager,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        val snackbarHostState = remember { SnackbarHostState() }

        var canPopUp by remember { mutableStateOf(false) }

        DisposableEffect(navController) {
            val listener = NavController.OnDestinationChangedListener { controller, _, _ ->
                canPopUp = controller.previousBackStackEntry != null
            }
            navController.addOnDestinationChangedListener(listener)
            onDispose { navController.removeOnDestinationChangedListener(listener) }
        }

        Scaffold(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    isBackButtonVisible = canPopUp,
                    onBackButtonClicked = { navController.popBackStack() }
                )
            }
        ) { paddings ->

            val offlineMode by isOffline.collectAsStateWithLifecycle()

            // If user is not connected to the internet show a snack bar to inform them.
            val notConnected = stringResource(R.string.not_connected)
            LaunchedEffect(offlineMode) {
                if (offlineMode) {
                    snackbarHostState.showSnackbar(
                        message = notConnected,
                        duration = SnackbarDuration.Indefinite
                    )
                }
            }

            NavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddings)
                    .consumedWindowInsets(paddings),
                navController = navController,
                startDestination = CARS_ON_MAP_FEATURE_ROUTE
            ) {
                navigationFactories.forEach {
                    it.create(this)
                }
            }

            featureIntentManager.featureIntent.collectWithLifecycle { intent ->
                when (intent) {
                    is NavigateIntent -> {
                        navController.navigate(intent.route)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun TopAppBar(
    isBackButtonVisible: Boolean,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(stringResource(id = R.string.app_name))
        },
        navigationIcon = {
            AnimatedVisibility(
                visible = isBackButtonVisible,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                IconButton(onClick = onBackButtonClicked) {
                    Icon(
                        Icons.Default.ArrowBack,
                        stringResource(id = R.string.go_back)
                    )
                }
            }
        },
        windowInsets = WindowInsets.safeDrawing.only(
            WindowInsetsSides.Top + WindowInsetsSides.Horizontal
        )
    )
}
