package com.example.solid.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.solid.data.core.InternetConnection
import com.example.solid.data.core.InternetConnectionImpl
import com.example.solid.data.local.PostDatabase
import com.example.solid.data.local.PostLocalDataSource
import com.example.solid.data.local.PostLocalDataSourceImpl
import com.example.solid.data.remote.PostApi
import com.example.solid.data.remote.PostRemoteDataSource
import com.example.solid.data.remote.PostRemoteDataSourceImpl
import com.example.solid.data.repository.PostRepositoryImpl
import com.example.solid.domain.repository.PostRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesPostDataSource(api: PostApi): PostRemoteDataSource {
        return PostRemoteDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun providesPostLocalDataSource(db: PostDatabase): PostLocalDataSource {
        return PostLocalDataSourceImpl(db.dao())
    }

    @Provides
    @Singleton
    fun providesPostRepository(
        remoteDataSource: PostRemoteDataSource,
        localDataSource: PostLocalDataSource,
        internetConnection: InternetConnection
    ): PostRepository {
        return PostRepositoryImpl(
            remoteDataSource,
            localDataSource,
            internetConnection,
        )
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

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext app: Context): PostDatabase {
        return Room.databaseBuilder(
            app,
            PostDatabase::class.java,
            "post-database",
        ).build()
    }

    @Provides
    @Singleton
    fun providesInternetConnection(@ApplicationContext app: Context): InternetConnection {
        return InternetConnectionImpl(app)
    }
}