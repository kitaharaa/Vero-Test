package com.kitaharaa.digitalapp.di.modules.singleton

import android.content.Context
import androidx.room.Room
import com.kitaharaa.digitalapp.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "vero_app_database"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideAuthDao(db: AppDatabase) = db.getAuthDao()

    @Provides
    @Singleton
    fun provideTaskInfoDao(db: AppDatabase) = db.getTaskInfoDao()
}