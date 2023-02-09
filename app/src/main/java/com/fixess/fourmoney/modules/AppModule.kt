package com.fixess.fourmoney.modules

import android.os.Build
import androidx.annotation.RequiresApi
import com.fixess.fourmoney.modules.AppModule_ProvideGsonFactory.create
import com.fixess.fourmoney.tools.LocalDateTimeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().registerTypeAdapter(LocalDate::class.java, LocalDateTimeConverter().nullSafe()).create()

}