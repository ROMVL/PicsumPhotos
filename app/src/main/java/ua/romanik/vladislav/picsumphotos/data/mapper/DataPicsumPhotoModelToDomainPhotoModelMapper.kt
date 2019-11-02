package ua.romanik.vladislav.picsumphotos.data.mapper

import ua.romanik.vladislav.picsumphotos.data.cloud.model.PicsumPhoto
import ua.romanik.vladislav.picsumphotos.domain.mapper.DataMapper
import ua.romanik.vladislav.picsumphotos.domain.model.Photo


class DataPicsumPhotoModelToDomainPhotoModelMapper : DataMapper<PicsumPhoto, Photo> {

    override fun convert(item: PicsumPhoto): Photo =
        Photo(item.id, item.author, item.width, item.height, item.url, "https://picsum.photos/id/${item.id}/${600}/${400}")

}