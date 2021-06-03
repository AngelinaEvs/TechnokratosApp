package ru.itis.regme.presenter.clients.choice.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.itis.regme.di.ViewModelKey
import ru.itis.regme.di.ViewModelModule
import ru.itis.regme.domain.ClientInterractor
import ru.itis.regme.domain.FindUseCase
import ru.itis.regme.presenter.calendar.CalendarViewModel
import ru.itis.regme.presenter.clients.choice.ChoiceClientsViewModel

@Module(includes = [ViewModelModule::class])
class ChoiceModule {

    @Provides
    @IntoMap
    @ViewModelKey(ChoiceClientsViewModel::class)
    fun provideViewModel(interractor: ClientInterractor): ViewModel {
        return ChoiceClientsViewModel(interractor)
    }

    @Provides
    fun provideViewModelCreator(fragment: Fragment, viewModelFactory: ViewModelProvider.Factory): ChoiceClientsViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(ChoiceClientsViewModel::class.java)
    }

}