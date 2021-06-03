package ru.itis.regme.presenter.clients.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.itis.regme.di.ScreenScope
import ru.itis.regme.presenter.clients.PhoneNumbersFragment

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