package ru.itis.regme.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.itis.regme.data.repository.FirebaseRepository
import ru.itis.regme.data.db.dao.ClientDao
import ru.itis.regme.data.repository.ClientRepositoryImpl
import ru.itis.regme.domain.interfaces.ClientRepository
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    @Singleton
    fun provideAppRepository(context: Context): FirebaseRepository = FirebaseRepository(context)

    @Provides
    @Singleton
    fun provideClientRepository(
        clientDao: ClientDao
    ): ClientRepository {
        return ClientRepositoryImpl(clientDao)
    }

}