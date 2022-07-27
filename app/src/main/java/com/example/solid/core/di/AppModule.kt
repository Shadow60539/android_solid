package com.example.solid.core.di

import com.example.solid.data.remote.PostApi
import com.example.solid.data.remote.PostDataSource
import com.example.solid.data.remote.PostDataSourceImpl
import com.example.solid.data.repository.PostRepositoryImpl
import com.example.solid.domain.repository.PostRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesAuthDataSource(api: PostApi): PostDataSource {
        return PostDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun providesAuthRepository(dataSource: PostDataSource): PostRepository {
        return PostRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun providesPostApi(moshi: Moshi): PostApi {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PostApi::class.java)
    }

    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}