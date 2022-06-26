package com.sccase.listpeople.di

import com.sccase.listpeople.data.local.dataprovider.DataSource
import com.sccase.listpeople.data.repository.UserRepository
import com.sccase.listpeople.data.repository.UserRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        dataSource: DataSource
    ): UserRepository = UserRepositoryImp(dataSource)
}