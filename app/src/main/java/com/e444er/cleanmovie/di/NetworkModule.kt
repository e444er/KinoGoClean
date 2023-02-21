package com.e444er.cleanmovie.di

import android.content.Context
import coil.ImageLoader
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.data.remote.RequestInterceptor
import com.e444er.cleanmovie.data.remote.TMDBApi
import com.e444er.cleanmovie.util.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(RequestInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideTmdbApi(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): TMDBApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TMDBApi::class.java)

    }

    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
        return  ImageLoader.Builder(context)
            .crossfade(500)
            .placeholder(R.drawable.loading_animate)
            .build()
    }
}