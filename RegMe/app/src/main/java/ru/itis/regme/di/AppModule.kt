package ru.itis.regme.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import ru.itis.regme.data.repository.FirebaseRepository
import ru.itis.regme.domain.FindUseCase
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideAppFindUseCase(firebaseRepository: FirebaseRepository): FindUseCase {
        return FindUseCase(firebaseRepository, Dispatchers.IO)
    }

    @Provides
    fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO
}