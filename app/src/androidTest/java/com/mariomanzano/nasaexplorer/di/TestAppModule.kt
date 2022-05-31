package com.mariomanzano.nasaexplorer.di

import android.app.Application
import androidx.room.Room
import com.mariomanzano.nasaexplorer.R
import com.mariomanzano.nasaexplorer.data.database.NasaDatabase
import com.mariomanzano.nasaexplorer.data.di.ApiEndPoint
import com.mariomanzano.nasaexplorer.data.di.ApiKey
import com.mariomanzano.nasaexplorer.data.di.AppModule
import com.mariomanzano.nasaexplorer.network.QueryInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.inMemoryDatabaseBuilder(
        app,
        NasaDatabase::class.java
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(db: NasaDatabase) = db.nasaDao()

    @Provides
    @ApiEndPoint
    fun providesApiEndPoint(app: Application): String = "http://localhost:8080"

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        queryInterceptor: QueryInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(queryInterceptor)
        .build()

    @Provides
    fun provideRestAdapter(@ApiEndPoint apiEndPoint: String, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(apiEndPoint)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
}