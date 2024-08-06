package com.example.kilvvr_cities_retrival_task

import android.app.Application
import com.example.kilvvr_cities_retrival_task.repository.CitiesRepository
import com.example.kilvvr_cities_retrival_task.repository.CitiesRepositoryImpl
import com.example.kilvvr_cities_retrival_task.ui.screens.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        /**
         * we start instantiating our repositories and view models by using koin
         */
        val appModule = module {
            /**
             * we create our data source repository as singleton.
             * passing the application context to its constructor.
             */
            single<CitiesRepository>{
                CitiesRepositoryImpl(get())
            }
            /**
             * passing our data repository to the viewmodel creation,
             * so we can avoid using viewmodel factories
             */
            viewModel { HomeViewModel(get()) }
        }

        startKoin {
            androidContext(this@MyApp)
            modules(appModule)
        }
    }
}