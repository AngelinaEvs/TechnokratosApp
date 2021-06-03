package ru.itis.regme.presenter.sign.up.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.itis.regme.di.ViewModelKey
import ru.itis.regme.di.ViewModelModule
import ru.itis.regme.domain.FindUseCase
import ru.itis.regme.presenter.sign.up.RegistrationViewModel

@Module(includes = [ViewModelModule::class])
class RegistrationModule {

    @Provides
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    fun provideViewModel(findUseCase: FindUseCase): ViewModel {
        return RegistrationViewModel(findUseCase)
    }

    @Provides
    fun provideViewModelCreator(fragment: Fragment, viewModelFactory: ViewModelProvider.Factory): RegistrationViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(RegistrationViewModel::class.java)
    }

}