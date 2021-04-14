package ru.itis.regme.presenter.sign.up.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.itis.regme.presenter.sign.up.RegistrationFragment

@Subcomponent(modules = [RegistrationModule::class])
interface RegistrationComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): RegistrationComponent
    }

    fun inject(registrationFragment: RegistrationFragment)
}