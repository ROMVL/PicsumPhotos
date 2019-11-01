package ua.romanik.vladislav.picsumphotos.domain.repository

import ua.romanik.vladislav.picsumphotos.domain.model.Photo

interface IPhotosRepository {

    suspend fun fetchPhotos(page: Int, limit: Int): List<Photo>

    suspend fun fetchPhotoById(id: Long): Photo
}