package ru.droidcat.starline.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.droidcat.starline.MainFeatureIntentManager
import ru.droidcat.starline.core.navigation.FeatureIntentManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MainModule {

    @Binds
    @Singleton
    fun provideFeatureIntentManager(impl: MainFeatureIntentManager): FeatureIntentManager
}
