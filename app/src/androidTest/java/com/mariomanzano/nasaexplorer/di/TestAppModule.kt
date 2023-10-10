package com.mariomanzano.nasaexplorer.di

import android.app.Application
import androidx.room.Room
import com.mariomanzano.nasaexplorer.R
import com.mariomanzano.nasaexplorer.data.database.EarthRoomDataSource
import com.mariomanzano.nasaexplorer.data.database.LastDbUpdateRoomDataSource
import com.mariomanzano.nasaexplorer.data.database.MarsRoomDataSource
import com.mariomanzano.nasaexplorer.data.database.NasaDatabase
import com.mariomanzano.nasaexplorer.data.database.PODRoomDataSource
import com.mariomanzano.nasaexplorer.data.di.ApiEndPoint
import com.mariomanzano.nasaexplorer.data.di.ApiKey
import com.mariomanzano.nasaexplorer.data.di.AppDataModule
import com.mariomanzano.nasaexplorer.data.di.AppModule
import com.mariomanzano.nasaexplorer.datasource.EarthLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.EarthRemoteDataSource
import com.mariomanzano.nasaexplorer.datasource.LastDbUpdateDataSource
import com.mariomanzano.nasaexplorer.datasource.MarsLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.MarsRemoteDataSource
import com.mariomanzano.nasaexplorer.datasource.PODLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.PODRemoteDataSource
import com.mariomanzano.nasaexplorer.network.DailyEarthService
import com.mariomanzano.nasaexplorer.network.DailyEarthServiceImpl
import com.mariomanzano.nasaexplorer.network.DailyPicturesService
import com.mariomanzano.nasaexplorer.network.DailyPicturesServiceImpl
import com.mariomanzano.nasaexplorer.network.EarthServerDataSource
import com.mariomanzano.nasaexplorer.network.MarsServerDataSource
import com.mariomanzano.nasaexplorer.network.MarsService
import com.mariomanzano.nasaexplorer.network.MarsServiceImpl
import com.mariomanzano.nasaexplorer.network.PODServerDataSource
import com.mariomanzano.nasaexplorer.network.QueryInterceptor
import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository
import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository
import com.mariomanzano.nasaexplorer.ui.screens.dailypicture.DailyPictureViewModel
import com.mariomanzano.nasaexplorer.usecases.GetLastPODUpdateDateUseCase
import com.mariomanzano.nasaexplorer.usecases.GetPODUseCase
import com.mariomanzano.nasaexplorer.usecases.RequestPODListUseCase
import com.mariomanzano.nasaexplorer.usecases.RequestPODSingleDayUseCase
import com.mariomanzano.nasaexplorer.usecases.UpdateLastPODUpdateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
                json()
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