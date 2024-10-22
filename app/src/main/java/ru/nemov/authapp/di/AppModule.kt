package ru.nemov.authapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.nemov.authapp.data.database.AccountsDataDatabase
import ru.nemov.authapp.data.database.common.AndroidLogcatLogger
import ru.nemov.authapp.data.database.common.AppDispatchers
import ru.nemov.authapp.data.database.common.Logger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOtpDatabase(
        @ApplicationContext context: Context
    ): AccountsDataDatabase {
        return AccountsDataDatabase(context)
    }

    @Provides
    @Singleton
    fun provideAppCoroutineDispatchers(): AppDispatchers = AppDispatchers()

    @Provides
    fun provideLogger(): Logger = AndroidLogcatLogger()
}