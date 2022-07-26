package com.example.solid.core.di

import com.example.solid.data.remote.AuthDataSource
import com.example.solid.data.remote.AuthDataSourceImpl
import com.example.solid.data.repository.AuthRepositoryImpl
import com.example.solid.domain.repository.AuthRepository
import com.example.solid.domain.usecase.LoginInUseCase
import org.koin.dsl.module

//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import org.koin.dsl.module
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//
//    @Provides
//    @Singleton
//    fun providesAuthDataSource(): AuthDataSource {
//        return AuthDataSourceImpl()
//    }
//
//    @Provides
//    @Singleton
//    fun providesAuthRepository(dataSource: AuthDataSource): AuthRepository {
//        return AuthRepositoryImpl(dataSource)
//    }
//
//}

val appModule = module {
    single<AuthDataSource> {
        AuthDataSourceImpl()
    }
    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }
    single<LoginInUseCase> {
        LoginInUseCase(get())
    }
}
