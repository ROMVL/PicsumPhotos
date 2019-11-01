package ua.romanik.vladislav.picsumphotos.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.romanik.vladislav.picsumphotos.BuildConfig
import ua.romanik.vladislav.picsumphotos.data.cloud.PicsumPhotosApi
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { providesHttpClient() }
    single { provideRetrofit(get()) }
    single { provideApi(get()) }
}

fun providesHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
    }

    val client = OkHttpClient.Builder()
    client.readTimeout(60, TimeUnit.SECONDS)
    client.writeTimeout(60, TimeUnit.SECONDS)
    client.connectTimeout(60, TimeUnit.SECONDS)
    client.addInterceptor(loggingInterceptor)

    return client.build()
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}

fun provideApi(retrofit: Retrofit): PicsumPhotosApi = retrofit.create(PicsumPhotosApi::class.java)