package com.fixess.fourmoney.dagger

import com.fixess.fourmoney.MainActivity
import com.fixess.fourmoney.database.DatabaseModule
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Component(modules = [AppModule::class,DatabaseModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson():Gson = GsonBuilder().create()


}