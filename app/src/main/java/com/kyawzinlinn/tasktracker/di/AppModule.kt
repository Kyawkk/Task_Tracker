package com.kyawzinlinn.tasktracker.di

import android.content.Context
import com.kyawzinlinn.tasktracker.data.repository.AuthenticationRepository
import com.kyawzinlinn.tasktracker.data.repository_impl.AuthenticationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }
    @Provides
    fun providesAuthenticationRepository(context: Context): AuthenticationRepository {
        return AuthenticationRepositoryImpl(context = context)
    }
}