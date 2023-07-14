package com.acidhand.currencyconverter.di

import android.content.Context
import com.acidhand.currencyconverter.utils.resourse_provider.IResourceProvider
import com.acidhand.currencyconverter.utils.resourse_provider.ResourceProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {

    @Singleton
    @Provides
    fun provideResourceProvider(@ApplicationContext context: Context): IResourceProvider {
        return ResourceProviderImpl(resources = context.resources)
    }
}