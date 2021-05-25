package ru.itis.regme.di

import android.content.Context
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import ru.itis.regme.common.ResourceManager
import ru.itis.regme.common.ResourceManagerImpl
import ru.itis.regme.data.repository.FirebaseRepository
import ru.itis.regme.domain.FindUseCase
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideAppFindUseCase(firebaseRepository: FirebaseRepository/*, coroutineContext: CoroutineContext*/): FindUseCase {
        return FindUseCase(firebaseRepository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideResourceManager(context: Context): ResourceManager {
        return ResourceManagerImpl(context)
    }

    @Provides
    fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO
}