package ru.droidcat.starline

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.droidcat.starline.core.navigation.FeatureIntent
import ru.droidcat.starline.core.navigation.FeatureIntentManager
import ru.droidcat.starline.core.navigation.di.MainImmediateScope
import javax.inject.Inject

class MainFeatureIntentManager @Inject constructor(
    @MainImmediateScope private val scope: CoroutineScope
) : FeatureIntentManager {

    private val intentChannel = Channel<FeatureIntent>(Channel.BUFFERED)

    override val featureIntent: Flow<FeatureIntent> = intentChannel.receiveAsFlow()

    override fun sendIntent(intent: FeatureIntent) {
        scope.launch {
            intentChannel.send(intent)
        }
    }
}
