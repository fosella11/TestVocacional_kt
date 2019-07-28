package com.softf.vocacional.di

import androidx.room.Room
import com.softf.vocacional.model.AppDatabase
import com.softf.vocacional.model.repository.AppRepository
import com.softf.vocacional.network.HeaderInterceptor
import com.softf.vocacional.network.NetworkService
import com.softf.vocacional.ui.splash.SplashViewModel
import com.softf.vocacional.utils.Constants
import com.softf.vocacional.utils.Prefs
import com.softf.vocacional.utils.SharedPreferencesHelper
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.softf.vocacional.ui.products.ProductsViewModel
import com.softf.vocacional.ui.result.ResultTestViewModel
import com.softf.vocacional.ui.test.TestViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single { SharedPreferencesHelper(get()) }
    single { Prefs(get()) }
    single { AppRepository(get(), get(), get()) }

    single { GsonBuilder().create() }
    single { GsonConverterFactory.create(get()) }
    single { CoroutineCallAdapterFactory() }
    single { HeaderInterceptor() }
    single { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }
    single {
        OkHttpClient().newBuilder().apply {
            addInterceptor(get<HeaderInterceptor>())
            addInterceptor(get<HttpLoggingInterceptor>())
            connectTimeout(0, TimeUnit.SECONDS)
            readTimeout(0, TimeUnit.SECONDS)
            writeTimeout(0, TimeUnit.SECONDS)
        }.build()
    }
    single {
        Retrofit.Builder().apply {
            client(get())
            baseUrl(Constants.BASE_URL)
            addConverterFactory(get<GsonConverterFactory>())
            addCallAdapterFactory(get<CoroutineCallAdapterFactory>())
        }.build()
    }
    single { get<Retrofit>().create(NetworkService::class.java) }
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "test_voc_db").build() }

    viewModel { SplashViewModel(get()) }
    viewModel { ProductsViewModel(get()) }
    viewModel { TestViewModel(get()) }
    viewModel { ResultTestViewModel(get()) }
}