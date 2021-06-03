package ru.itis.regme.presenter.sign.`in`.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.itis.regme.di.ScreenScope
import ru.itis.regme.presenter.sign.`in`.LoginFragment

@Subcomponent(
        modules = [SignInModule::class]
)
@ScreenScope
interface SignInComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(
                @BindsInstance fragment: Fragment
        ): SignInComponent

    }

    fun inject(signInFragment: LoginFragment)

}