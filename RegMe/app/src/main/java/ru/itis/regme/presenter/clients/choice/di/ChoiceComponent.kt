package ru.itis.regme.presenter.clients.choice.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.itis.regme.di.ScreenScope
import ru.itis.regme.presenter.clients.choice.ChoiceClientsFragment

@Subcomponent(
    modules = [ChoiceModule::class]
)
@ScreenScope
interface ChoiceComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: Fragment
        ): ChoiceComponent
    }

    fun inject(choiceClientsFragment: ChoiceClientsFragment)
}