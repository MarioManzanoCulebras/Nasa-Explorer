package com.mariomanzano.nasaexplorer.data.di

import android.app.Application
import androidx.room.Room
import com.mariomanzano.nasaexplorer.R
import com.mariomanzano.nasaexplorer.data.database.*
import com.mariomanzano.nasaexplorer.datasource.*
import com.mariomanzano.nasaexplorer.network.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.gson.gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @ApiKey
    fun providePrivateApiKey(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        NasaDatabase::class.java,
        "nasa-db"
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(db: NasaDatabase) = db.nasaDao()

    @Provides
    @ApiEndPoint
    fun providesApiEndPoint(app: Application): String = app.getString(R.string.api_endPoint)

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
    fun provideKtorHttpClient(
        @ApiEndPoint baseUrl: String,
        loggingInterceptor: HttpLoggingInterceptor,
        queryInterceptor: QueryInterceptor
    ): HttpClient =
        HttpClient(OkHttp) {
            expectSuccess = true
            engine {
                addInterceptor(loggingInterceptor)
                addInterceptor(queryInterceptor)
            }
            defaultRequest {
                url(baseUrl)
            }
            install(ContentNegotiation) {
                gson()
            }
        }

    @Provides
    fun provideDailyPictureService(ktor: HttpClient): DailyPicturesService =
        DailyPicturesServiceImpl(ktor)

    @Provides
    fun provideDailyEarthServiceService(ktor: HttpClient): DailyEarthService =
        DailyEarthServiceImpl(ktor)

    @Provides
    fun provideMarsService(ktor: HttpClient): MarsService = MarsServiceImpl(ktor)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {
    @Binds
    abstract fun bindPODLocalDataSource(localDataSource: PODRoomDataSource): PODLocalDataSource

    @Binds
    abstract fun bindEarthLocalDataSource(localDataSource: EarthRoomDataSource): EarthLocalDataSource

    @Binds
    abstract fun bindMarsLocalDataSource(localDataSource: MarsRoomDataSource): MarsLocalDataSource

    @Binds
    abstract fun bindLastDbUpdateLocalDataSource(localDataSource: LastDbUpdateRoomDataSource): LastDbUpdateDataSource

    @Binds
    abstract fun bindPODemoteDataSource(remoteDataSource: PODServerDataSource): PODRemoteDataSource

    @Binds
    abstract fun bindEarthRemoteDataSource(remoteDataSource: EarthServerDataSource): EarthRemoteDataSource

    @Binds
    abstract fun bindMarsRemoteDataSource(remoteDataSource: MarsServerDataSource): MarsRemoteDataSource

}