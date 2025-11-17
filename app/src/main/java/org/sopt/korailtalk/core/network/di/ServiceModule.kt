package org.sopt.korailtalk.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.korailtalk.data.service.DummyService
import org.sopt.korailtalk.data.service.KorailTalkApiService
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideDummyApi(retrofit: Retrofit): DummyService = retrofit.create(DummyService::class.java)

    @Provides
    @Singleton
    fun provideKorailTalkApi(retrofit: Retrofit): KorailTalkApiService = retrofit.create(KorailTalkApiService::class.java)
}