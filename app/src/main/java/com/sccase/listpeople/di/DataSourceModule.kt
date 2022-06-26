package com.sccase.listpeople.di

import com.sccase.listpeople.data.local.dataprovider.DataSource
import com.sccase.listpeople.data.local.dataprovider.DataSourceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideDataProvider(): DataSource = DataSourceImp()
}