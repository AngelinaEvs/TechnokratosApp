package ru.itis.regme.presenter.profile.di

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
import ru.itis.regme.domain.FindUseCase
import ru.itis.regme.presenter.profile.ProfileViewModel
import ru.itis.regme.presenter.sign.`in`.LoginViewModel

@Module(includes = [ViewModelModule::class])
class ProfileModule {

    @Provides
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun provideViewModel(findUseCase: FindUseCase): ViewModel {
        return ProfileViewModel(findUseCase)
    }

    @Provides
    fun provideViewModelCreator(fragment: Fragment, viewModelFactory: ViewModelProvider.Factory): ProfileViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(ProfileViewModel::class.java)
    }

}