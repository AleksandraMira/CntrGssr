package com.example.cntrgssr.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.cntrgssr.core.data.CountryDatabase
import com.example.cntrgssr.core.dataStore.PreferencesDataStoreRepository
import com.example.cntrgssr.core.dataStore.PreferencesDataStoreRepositoryImpl
import com.example.cntrgssr.core.dataStore.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDataSource(
        @ApplicationContext appContext: Context,
    ): PreferencesDataStoreRepository = PreferencesDataStoreRepositoryImpl(
        preferences = appContext.dataStore,
    )

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        CountryDatabase::class.java,
        "country_database"
    ).build()

    @Provides
    @Singleton
    fun provideCountryDao(db: CountryDatabase) = db.countryDao
}