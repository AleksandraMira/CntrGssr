package com.example.cntrgssr.di

import android.content.Context
import com.example.cntrgssr.core.util.ResourceResolver
import com.example.cntrgssr.core.util.ResourceResolverImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {
    @Provides
    @Singleton
    fun provideResourceResolver(
        @ApplicationContext appContext: Context,
    ): ResourceResolver = ResourceResolverImpl(appContext)
}