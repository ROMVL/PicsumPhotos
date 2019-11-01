package ua.romanik.vladislav.picsumphotos.domain.mapper


interface DataMapper<T, R> {
    fun convert(item: T): R
}