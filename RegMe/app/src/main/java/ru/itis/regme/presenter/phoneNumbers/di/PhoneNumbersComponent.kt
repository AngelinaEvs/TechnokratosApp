package ru.itis.regme.presenter.phoneNumbers.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.itis.regme.di.ScreenScope
import ru.itis.regme.presenter.phoneNumbers.PhoneNumbersFragment
import ru.itis.regme.presenter.splash.SplashFragment
import ru.itis.regme.presenter.splash.di.SplashComponent
import ru.itis.regme.presenter.splash.di.SplashModule

@Subcomponent(
    modules = [PhoneNumbersModule::class]
)
@ScreenScope
interface PhoneNumbersComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: Fragment
        ): PhoneNumbersComponent
    }

    fun inject(phoneNumbersFragment: PhoneNumbersFragment)

}