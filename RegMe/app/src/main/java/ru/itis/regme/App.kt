package ru.itis.regme

import android.app.Application
import ru.itis.regme.di.AppComponent
import ru.itis.regme.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build()
    }

}