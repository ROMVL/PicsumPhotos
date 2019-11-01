package ua.romanik.vladislav.picsumphotos.domain.usecase

import ua.romanik.vladislav.picsumphotos.data.mapper.CloudErrorMapper
import ua.romanik.vladislav.picsumphotos.domain.model.Photo
import ua.romanik.vladislav.picsumphotos.domain.repository.IPhotosRepository
import ua.romanik.vladislav.picsumphotos.domain.usecase.base.UseCase

class FetchPhotosUseCase(
    private val photosRepository: IPhotosRepository,
    errorCloudErrorMapper: CloudErrorMapper
) : UseCase<List<Photo>>(errorCloudErrorMapper) {
    override suspend fun executeOnBackground() = photosRepository.fetchPhotos(1, 10)
}