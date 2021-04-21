package ru.itis.regme.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.itis.regme.data.di.RepoModule
import ru.itis.regme.presenter.calendar.di.CalendarComponent
import ru.itis.regme.presenter.phoneNumbers.di.PhoneNumbersComponent
import ru.itis.regme.presenter.profile.di.ProfileComponent
import ru.itis.regme.presenter.sign.`in`.di.SignInComponent
import ru.itis.regme.presenter.sign.up.di.RegistrationComponent
import ru.itis.regme.presenter.splash.di.SplashComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
//        DbModule::class,
//        NetworkModule::class,
        AppModule::class,
        RepoModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Context): Builder

        fun build(): AppComponent
    }

    fun signInComponentFactory(): SignInComponent.Factory

    fun registrationComponentFactory(): RegistrationComponent.Factory

    fun profileComponentFactory(): ProfileComponent.Factory

    fun calendarComponentFactory(): CalendarComponent.Factory

    fun splashComponentFactory(): SplashComponent.Factory

    fun phoneNumberComponentFactory(): PhoneNumbersComponent.Factory
}