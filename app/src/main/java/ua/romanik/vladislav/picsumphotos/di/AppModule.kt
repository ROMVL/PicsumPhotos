package ua.romanik.vladislav.picsumphotos.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ua.romanik.vladislav.picsumphotos.data.mapper.CloudErrorMapper
import ua.romanik.vladislav.picsumphotos.data.mapper.DataPicsumPhotoModelToDomainPhotoModelMapper
import ua.romanik.vladislav.picsumphotos.data.repository.PicsumPhotosRepository
import ua.romanik.vladislav.picsumphotos.domain.mapper.DataMapper
import ua.romanik.vladislav.picsumphotos.domain.repository.IPhotosRepository
import ua.romanik.vladislav.picsumphotos.domain.usecase.FetchPhotosUseCase
import ua.romanik.vladislav.picsumphotos.presentation.ui.photoslist.PhotosListViewModel

val viewModelModule = module {
    viewModel { PhotosListViewModel(get()) }
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
}

val appModules = listOf(networkModule, mapperModule, repositoryModule, useCaseModule, viewModelModule)