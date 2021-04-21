package ru.itis.regme.presenter.splash.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.itis.regme.di.ScreenScope
import ru.itis.regme.presenter.profile.ProfileFragment
import ru.itis.regme.presenter.profile.di.ProfileComponent
import ru.itis.regme.presenter.profile.di.ProfileModule
import ru.itis.regme.presenter.splash.SplashFragment

@Subcomponent(
    modules = [SplashModule::class]
)
@ScreenScope
interface SplashComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
                @BindsInstance fragment: Fragment
        ): SplashComponent
    }

    fun inject(splashFragment: SplashFragment)

}