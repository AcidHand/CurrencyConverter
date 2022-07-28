package com.acidhand.currencyconverter.di

import android.content.Context
import com.acidhand.currencyconverter.data.CurrencyUseCaseImpl
import com.acidhand.currencyconverter.data.database.AppDatabase
import com.acidhand.currencyconverter.data.database.dao.AppDao
import com.acidhand.currencyconverter.data.database.repository.IRoomRepository
import com.acidhand.currencyconverter.data.database.repository.RoomRepositoryImpl
import com.acidhand.currencyconverter.domain.usecase.ICurrencyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideCurrencyUseCase(@ApplicationContext context: Context): ICurrencyUseCase {
        return CurrencyUseCaseImpl(context = context)
    }

    @Singleton
    @Provides
    fun getAppDB(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getAppDb(context = context)
    }

    @Singleton
    @Provides
    fun getDao(appDB: AppDatabase): AppDao {
        return appDB.getDao()
    }

    @Singleton
    @Provides
    fun provideCurrencyRepository(dao: AppDao): IRoomRepository {
        return RoomRepositoryImpl(appDao = dao)
    }
}
