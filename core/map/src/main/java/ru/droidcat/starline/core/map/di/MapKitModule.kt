package ru.droidcat.starline.core.map.di

import android.content.Context
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.droidcat.starline.core.map.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapKitModule {

    @Provides
    @Singleton
    fun provideMapKit(
        @ApplicationContext context: Context
    ): MapKit {
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_KEY)
        MapKitFactory.initialize(context)
        return MapKitFactory.getInstance()
    }
}
