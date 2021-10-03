package gortea.jgmax.cats.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gortea.jgmax.cats.navigation.coordinator.Coordinator
import gortea.jgmax.cats.navigation.coordinator.CoordinatorImpl
import gortea.jgmax.cats.navigation.storage.NavStorage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoordinatorModule {
    @Provides
    @Singleton
    fun provideNavStorage(): NavStorage = NavStorage()
}

@Module
@InstallIn(SingletonComponent::class)
interface CoordinatorBinder {
    @Binds
    @Singleton
    fun bindCoordinator(coordinatorImpl: CoordinatorImpl): Coordinator
}
