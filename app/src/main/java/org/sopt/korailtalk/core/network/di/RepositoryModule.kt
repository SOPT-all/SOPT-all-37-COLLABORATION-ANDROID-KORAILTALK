package org.sopt.korailtalk.core.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.korailtalk.data.repositoryImpl.DummyRepositoryImpl
import org.sopt.korailtalk.data.repositoryImpl.KorailTalkRepositoryImpl
import org.sopt.korailtalk.domain.repository.DummyRepository
import org.sopt.korailtalk.domain.repository.KorailTalkRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDummyRepository(dummyRepositoryImpl: DummyRepositoryImpl): DummyRepository

    @Binds
    @Singleton
    abstract fun bindKorailTalkRepository(korailTalkRepositoryImpl: KorailTalkRepositoryImpl): KorailTalkRepository
}