package ru.itis.regme.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.itis.regme.data.AppRepository
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    @Singleton
    fun provideAppRepository(context: Context): AppRepository = AppRepository(context)

}