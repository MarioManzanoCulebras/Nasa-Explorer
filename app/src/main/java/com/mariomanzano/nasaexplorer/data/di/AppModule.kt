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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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
    fun provideRestAdapter(@ApiEndPoint apiEndPoint: String, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(apiEndPoint)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    fun provideDailyPictureService(restAdapter: Retrofit): DailyPicturesService =
        restAdapter.create()

    @Provides
    fun provideDailyEarthServiceService(restAdapter: Retrofit): DailyEarthService =
        restAdapter.create()

    @Provides
    fun provideMarsService(restAdapter: Retrofit): MarsService = restAdapter.create()

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