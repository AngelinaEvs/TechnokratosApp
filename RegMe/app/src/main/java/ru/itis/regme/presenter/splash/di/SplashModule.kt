package ru.itis.regme.presenter.splash.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.itis.regme.di.ViewModelKey
import ru.itis.regme.di.ViewModelModule
import ru.itis.regme.domain.FindUseCase
import ru.itis.regme.presenter.splash.SplashViewModel

@Module(includes = [ViewModelModule::class])
class SplashModule {

    @Provides
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    fun provideViewModel(findUseCase: FindUseCase): ViewModel {
        return SplashViewModel(findUseCase)
    }

    @Provides
    fun provideViewModelCreator(fragment: Fragment, viewModelFactory: ViewModelProvider.Factory): SplashViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(SplashViewModel::class.java)
    }

}