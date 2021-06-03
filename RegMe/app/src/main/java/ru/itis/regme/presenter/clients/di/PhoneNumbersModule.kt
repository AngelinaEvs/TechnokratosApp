package ru.itis.regme.presenter.clients.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.itis.regme.di.ViewModelKey
import ru.itis.regme.di.ViewModelModule
import ru.itis.regme.domain.FindUseCase
import ru.itis.regme.presenter.clients.PhoneNumbersViewModel

@Module(includes = [ViewModelModule::class])
class PhoneNumbersModule {

    @Provides
    @IntoMap
    @ViewModelKey(PhoneNumbersViewModel::class)
    fun provideViewModel(findUseCase: FindUseCase): ViewModel {
        return PhoneNumbersViewModel(findUseCase)
    }

    @Provides
    fun provideViewModelCreator(fragment: Fragment, viewModelFactory: ViewModelProvider.Factory): PhoneNumbersViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(PhoneNumbersViewModel::class.java)
    }

}