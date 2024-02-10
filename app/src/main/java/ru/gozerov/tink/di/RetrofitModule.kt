package ru.gozerov.tink.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.gozerov.data.movies.remote.MoviesApi
import ru.gozerov.tink.utils.ApiConstants
import javax.inject.Singleton

@Module
interface RetrofitModule {

    @Provides
    @Singleton
    fun provideMoviesApi(): MoviesApi {
        val moshiConverterFactory = MoshiConverterFactory.create(Moshi.Builder().build())
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader("x-api-key", ApiConstants.API_KEY)
                chain.proceed(builder.build())
            }
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(client)
            .addConverterFactory(moshiConverterFactory)
            .build()
        return retrofit.create(MoviesApi::class.java)
    }

}