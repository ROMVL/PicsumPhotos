package ua.romanik.vladislav.picsumphotos.data.repository

import ua.romanik.vladislav.picsumphotos.data.cloud.PicsumPhotosApi
import ua.romanik.vladislav.picsumphotos.data.mapper.DataPicsumPhotoModelToDomainPhotoModelMapper
import ua.romanik.vladislav.picsumphotos.domain.model.Photo
import ua.romanik.vladislav.picsumphotos.domain.repository.IPhotosRepository

class PicsumPhotosRepository(
    private val picsumPhotosApi: PicsumPhotosApi,
    private val picsumPhotoModelToDomainPhotoModelMapper: DataPicsumPhotoModelToDomainPhotoModelMapper
) : IPhotosRepository {

    override suspend fun fetchPhotos(page: Int, limit: Int): List<Photo> {
        return picsumPhotosApi.fetchPhotos(page, limit).map {
            picsumPhotoModelToDomainPhotoModelMapper.convert(it)
        }
    }

    override suspend fun fetchPhotoById(id: Long): Photo {
        return picsumPhotosApi.fetchPhotoById(id).let { picsumPhotoModelToDomainPhotoModelMapper.convert(it) }
    }
}