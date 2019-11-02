package ua.romanik.vladislav.picsumphotos.data.cloud

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ua.romanik.vladislav.picsumphotos.data.cloud.model.PicsumPhoto

interface PicsumPhotosApi {

    @GET("/v2/list")
    suspend fun fetchPhotos(
        @Query("page") page: Long,
        @Query("limit") limit: Int
    ): List<PicsumPhoto>

    @GET("/id/{photoId}/info")
    suspend fun fetchPhotoById(@Path("photoId") photoId: Long): PicsumPhoto

}