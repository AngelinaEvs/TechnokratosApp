package ru.itis.regme.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.itis.regme.data.db.AppDatabase
import ru.itis.regme.data.db.dao.ClientDao
import javax.inject.Singleton

@Module
class DBModule {

    @Provides
    @Singleton
    fun provideDB(context: Context): AppDatabase {
        return AppDatabase.get(context)
    }

    @Provides
    @Singleton
    fun provediClientDao(database: AppDatabase): ClientDao = database.clientsDao()
}