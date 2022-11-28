package ru.droidcat.starline.core.network.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import ru.droidcat.starline.core.domain.NetworkDataSource
import ru.droidcat.starline.core.domain.NetworkMonitor
import ru.droidcat.starline.core.network.fake.FakeAssetManager
import ru.droidcat.starline.core.network.fake.FakeNetworkDataSource
import ru.droidcat.starline.core.network.util.ConnectivityManagerNetworkMonitor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    internal fun provideNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    internal fun provideFakeAssetManager(
        @ApplicationContext context: Context
    ): FakeAssetManager = FakeAssetManager(context.assets::open)

    @Provides
    @Singleton
    fun provideNetworkMonitor(
        @ApplicationContext context: Context
    ): NetworkMonitor = ConnectivityManagerNetworkMonitor(context)

    @Provides
    @Singleton
    fun provideNetworkDataSource(
        networkJson: Json,
        assetManager: FakeAssetManager
    ): NetworkDataSource {
        return FakeNetworkDataSource(networkJson, assetManager)
    }
}
