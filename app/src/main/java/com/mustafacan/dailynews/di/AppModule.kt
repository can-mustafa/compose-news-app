package com.mustafacan.dailynews.di

import com.mustafacan.dailynews.api.NewsApi
import com.mustafacan.dailynews.repository.NewsRepository
import com.mustafacan.dailynews.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NewsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideNewsRepository(api: NewsApi) = NewsRepository(api)
}