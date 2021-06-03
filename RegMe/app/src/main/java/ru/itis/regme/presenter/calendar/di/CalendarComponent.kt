package ru.itis.regme.presenter.calendar.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.itis.regme.di.ScreenScope
import ru.itis.regme.presenter.calendar.CalendarFragment
import ru.itis.regme.presenter.profile.ProfileFragment

@Subcomponent(
        modules = [CalendarModule::class]
)
@ScreenScope
interface CalendarComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
                @BindsInstance fragment: Fragment
        ): CalendarComponent
    }

    fun inject(calendarFragment: CalendarFragment)

}