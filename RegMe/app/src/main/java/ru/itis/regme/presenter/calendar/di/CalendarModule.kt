package ru.itis.regme.presenter.calendar.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.multibindings.IntoMap
import ru.itis.regme.di.ScreenScope
import ru.itis.regme.di.ViewModelKey
import ru.itis.regme.di.ViewModelModule
import ru.itis.regme.domain.ClientInterractor
import ru.itis.regme.domain.FindUseCase
import ru.itis.regme.presenter.calendar.CalendarViewModel
import ru.itis.regme.presenter.profile.ProfileViewModel
import ru.itis.regme.presenter.sign.`in`.LoginViewModel

@Module(includes = [ViewModelModule::class])
class CalendarModule {

    @Provides
    @IntoMap
    @ViewModelKey(CalendarViewModel::class)
    fun provideViewModel(findUseCase: FindUseCase, interractor: ClientInterractor): ViewModel {
        return CalendarViewModel(findUseCase, interractor)
    }

    @Provides
    fun provideViewModelCreator(fragment: Fragment, viewModelFactory: ViewModelProvider.Factory): CalendarViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(CalendarViewModel::class.java)
    }

}