package ua.romanik.vladislav.picsumphotos.domain.usecase

import ua.romanik.vladislav.picsumphotos.data.mapper.CloudErrorMapper
import ua.romanik.vladislav.picsumphotos.domain.model.Photo
import ua.romanik.vladislav.picsumphotos.domain.repository.IPhotosRepository
import ua.romanik.vladislav.picsumphotos.domain.usecase.base.UseCase

class FetchPhotoDetailsUseCase(
    private val photosRepository: IPhotosRepository,
    errorMapper: CloudErrorMapper
) : UseCase<Photo>(errorMapper) {

    var photoId: Long = 0

    override suspend fun executeOnBackground(): Photo = photosRepository.fetchPhotoById(photoId)
}