package ua.romanik.vladislav.picsumphotos

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ua.romanik.vladislav.picsumphotos.di.appModules

class PicsumPhotosApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PicsumPhotosApp)
            modules(appModules)
        }
    }

}