package ru.itis.regme.presenter.profile.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.itis.regme.di.ScreenScope
import ru.itis.regme.presenter.profile.ProfileFragment
import ru.itis.regme.presenter.sign.`in`.LoginFragment

@Subcomponent(
        modules = [ProfileModule::class]
)
@ScreenScope
interface ProfileComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
                @BindsInstance fragment: Fragment
        ): ProfileComponent
    }

    fun inject(profileFragment: ProfileFragment)

}