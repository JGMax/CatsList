package gortea.jgmax.cats.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gortea.jgmax.cats.catslist.repository.CatsListRepository
import gortea.jgmax.cats.catslist.repository.ListRepository

@Module
@InstallIn(SingletonComponent::class)
interface CatsRepositoryBinder {
    @Binds
    fun bindCatsRepository(catsListRepository: CatsListRepository): ListRepository
}