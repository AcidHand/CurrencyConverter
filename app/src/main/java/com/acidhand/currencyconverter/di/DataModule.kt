package com.acidhand.currencyconverter.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.acidhand.currencyconverter.data.AccessUseCaseImpl
import com.acidhand.currencyconverter.data.CurrencyUseCaseImpl
import com.acidhand.currencyconverter.data.database.AppDatabase
import com.acidhand.currencyconverter.data.database.dao.AppDao
import com.acidhand.currencyconverter.data.database.repository.IRoomRepository
import com.acidhand.currencyconverter.data.database.repository.RoomRepositoryImpl
import com.acidhand.currencyconverter.domain.usecase.IAccessUseCase
import com.acidhand.currencyconverter.domain.usecase.ICurrencyUseCase
import com.acidhand.currencyconverter.utils.CONST.USER_SHARED_PREFS
import com.acidhand.currencyconverter.utils.DataStoreUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    private val Context.dataStore: DataStore<Preferences>
            by preferencesDataStore(name = USER_SHARED_PREFS)

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

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

    @Singleton
    @Provides
    fun provideGetCodeAttemptsLeftUseCase(dataStoreUtils: DataStoreUtils): IAccessUseCase {
        return AccessUseCaseImpl(dataStore = dataStoreUtils)
    }
}
