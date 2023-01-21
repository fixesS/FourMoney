package com.fixess.fourmoney.database

import android.content.Context
import androidx.room.Room
import com.fixess.fourmoney.dagger.ApplicationComponent
import com.fixess.fourmoney.database.dao.DAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            FourMoneyDatabase::class.java,
            "fourmoney_database"
        ).build()

    @Provides
    @Singleton
    fun provideDAO(database: FourMoneyDatabase): DAO {
        return database.DAO()
    }
}