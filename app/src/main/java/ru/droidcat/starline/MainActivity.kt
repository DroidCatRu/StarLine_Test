package ru.droidcat.starline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.coroutineScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.droidcat.starline.core.domain.NetworkMonitor
import ru.droidcat.starline.core.navigation.FeatureIntentManager
import ru.droidcat.starline.core.navigation.NavigationFactory
import ru.droidcat.starline.core.ui.theme.StarLineTheme
import ru.droidcat.starline.ui.StarLineApp
import javax.inject.Inject

const val DEFAULT_FLOW_TIMEOUT = 5_000L

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @Inject
    lateinit var navigationFactories: @JvmSuppressWildcards Set<NavigationFactory>

    @Inject
    lateinit var featureIntentManager: FeatureIntentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val isOffline = networkMonitor.isOnline
            .map(Boolean::not)
            .stateIn(
                scope = lifecycle.coroutineScope,
                started = SharingStarted.WhileSubscribed(DEFAULT_FLOW_TIMEOUT),
                initialValue = false
            )

        setContent {
            StarLineTheme {
                StarLineApp(
                    isOffline = isOffline,
                    navigationFactories = navigationFactories.toImmutableSet(),
                    featureIntentManager = featureIntentManager
                )
            }
        }
    }
}
