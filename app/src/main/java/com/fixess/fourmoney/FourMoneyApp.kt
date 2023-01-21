package com.fixess.fourmoney

import android.app.Application
import android.content.Context
import com.fixess.fourmoney.dagger.ApplicationComponent
import com.fixess.fourmoney.dagger.DaggerApplicationComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FourMoneyApp: Application() {
    lateinit var appComponent : ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.create()
    }
}

val Context.appComponent: ApplicationComponent
    get() = when(this){
        is FourMoneyApp -> appComponent
        else -> this.applicationContext.appComponent
    }