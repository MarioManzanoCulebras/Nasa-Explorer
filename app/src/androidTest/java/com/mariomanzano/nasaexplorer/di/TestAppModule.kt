package com.mariomanzano.nasaexplorer.di

import android.app.Application
import androidx.room.Room
import com.mariomanzano.nasaexplorer.R
import com.mariomanzano.nasaexplorer.data.database.*
import com.mariomanzano.nasaexplorer.data.di.ApiEndPoint
import com.mariomanzano.nasaexplorer.data.di.ApiKey
import com.mariomanzano.nasaexplorer.data.di.AppDataModule
import com.mariomanzano.nasaexplorer.data.di.AppModule
import com.mariomanzano.nasaexplorer.datasource.*
import com.mariomanzano.nasaexplorer.network.*
import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository
import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository
import com.mariomanzano.nasaexplorer.ui.screens.dailypicture.DailyPictureViewModel
import com.mariomanzano.nasaexplorer.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class, AppDataModule::class]
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
    fun providesApiEndPoint(): String = "http://localhost:8080"

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

    @Provides
    fun provideDailyPictureViewModel(
        getPODUseCase: GetPODUseCase,
        requestPODListUseCase: RequestPODListUseCase,
        requestPODSingleDayUseCase: RequestPODSingleDayUseCase,
        getLastPODUpdateDateUseCase: GetLastPODUpdateDateUseCase,
        updateLastPODUpdateUseCase: UpdateLastPODUpdateUseCase
    ): DailyPictureViewModel =
        DailyPictureViewModel(
            getPODUseCase,
            requestPODListUseCase,
            requestPODSingleDayUseCase,
            getLastPODUpdateDateUseCase,
            updateLastPODUpdateUseCase
        )

    @Provides
    fun provideGetPODUseCase(repository: DailyPicturesRepository): GetPODUseCase =
        GetPODUseCase(repository)

    @Provides
    fun provideRequestPODListUseCase(repository: DailyPicturesRepository): RequestPODListUseCase =
        RequestPODListUseCase(repository)

    @Provides
    fun providePODSingleDayUseCase(repository: DailyPicturesRepository): RequestPODSingleDayUseCase =
        RequestPODSingleDayUseCase(repository)

    @Provides
    fun provideGetLastPODUpdateUseCase(repository: LastDbUpdateRepository): GetLastPODUpdateDateUseCase =
        GetLastPODUpdateDateUseCase(repository)

    @Provides
    fun provideLastDbUpdateRepository(dataSource: LastDbUpdateRoomDataSource): LastDbUpdateRepository =
        LastDbUpdateRepository(dataSource)

    @Provides
    fun provideLastDbUpdateDataSource(db: NasaDatabase): LastDbUpdateDataSource =
        LastDbUpdateRoomDataSource(db.nasaDao())

    @Provides
    fun provideUpdateLastPODUpdateUseCase(repository: LastDbUpdateRepository): UpdateLastPODUpdateUseCase =
        UpdateLastPODUpdateUseCase(repository)

    @Provides
    fun provideDailyPicturesRepository(
        local: PODLocalDataSource,
        remote: PODRemoteDataSource
    ): DailyPicturesRepository = DailyPicturesRepository(local, remote)

    @Provides
    fun providePODLocalDataSource(db: NasaDatabase): PODLocalDataSource =
        PODRoomDataSource(db.nasaDao())

    @Provides
    fun provideEarthLocalDataSource(db: NasaDatabase): EarthLocalDataSource =
        EarthRoomDataSource(db.nasaDao())

    @Provides
    fun provideMarsLocalDataSource(db: NasaDatabase): MarsLocalDataSource =
        MarsRoomDataSource(db.nasaDao())

    @Provides
    fun providePODRemoteDataSource(dailyPictureService: DailyPicturesService): PODRemoteDataSource =
        PODServerDataSource(dailyPictureService)

    @Provides
    fun provideEarthRemoteDataSource(dailyEarthService: DailyEarthService): EarthRemoteDataSource =
        EarthServerDataSource(dailyEarthService)

    @Provides
    fun provideMarsRemoteDataSource(marsService: MarsService): MarsRemoteDataSource =
        MarsServerDataSource(marsService)
}