package ru.droidcat.starline.feature.carsonmap.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import ru.droidcat.starline.core.navigation.FeatureIntentManager
import ru.droidcat.starline.core.navigation.NavigationFactory
import ru.droidcat.starline.feature.carsonmap.CarsOnMapNavigationFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CarsOnMapFeatureModule {

    @Provides
    @Singleton
    @IntoSet
    fun bindCarsOnMapNavigationFactory(
        featureIntentManager: FeatureIntentManager
    ): NavigationFactory {
        return CarsOnMapNavigationFactory(featureIntentManager)
    }
}
