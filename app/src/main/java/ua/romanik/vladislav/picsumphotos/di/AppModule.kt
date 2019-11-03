package ua.romanik.vladislav.picsumphotos.di

import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ua.romanik.vladislav.picsumphotos.data.mapper.CloudErrorMapper
import ua.romanik.vladislav.picsumphotos.data.mapper.DataPicsumPhotoModelToDomainPhotoModelMapper
import ua.romanik.vladislav.picsumphotos.data.repository.PicsumPhotosRepository
import ua.romanik.vladislav.picsumphotos.domain.repository.IPhotosRepository
import ua.romanik.vladislav.picsumphotos.domain.usecase.FetchPhotoDetailsUseCase
import ua.romanik.vladislav.picsumphotos.domain.usecase.FetchPhotosUseCase
import ua.romanik.vladislav.picsumphotos.presentation.ui.photodetails.PhotoDetailsViewModel
import ua.romanik.vladislav.picsumphotos.presentation.ui.photoslist.PhotosListViewModel

object KoinProperty {
    val photoId = "photoId"
}

val viewModelModule = module {
    viewModel { PhotosListViewModel(get()) }
    viewModel { PhotoDetailsViewModel(get(), getProperty(KoinProperty.photoId), androidApplication()) }
}

val mapperModule = module {
    single { CloudErrorMapper() }
    single { DataPicsumPhotoModelToDomainPhotoModelMapper() }
}

val repositoryModule = module {
    single { PicsumPhotosRepository(get(), get()) as IPhotosRepository }
}

val useCaseModule = module {
    single { FetchPhotosUseCase(get(), get()) }
    single { FetchPhotoDetailsUseCase(get(), get()) }
}

val appModules = listOf(networkModule, mapperModule, repositoryModule, useCaseModule, viewModelModule)