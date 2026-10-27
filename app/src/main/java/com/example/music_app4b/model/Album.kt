package com.example.music_app4b.model

data class Album(
    val wrapperType: String?,
    val collectionType: String?,
    val artistId: Long?,
    val collectionId: Long?,
    val artistName: String?,
    val collectionName: String?,
    val artistViewUrl: String?,
    val collectionViewUrl: String?,
    val artworkUrl100: String?,
    val collectionPrice: Double?,
    val trackCount: Int?,
    val releaseDate: String?
){

    //Metodo para obtener la imagen maa grande
    fun obtenerImagenGrande() : String{
        return artworkUrl100?.replace("100x100bb.jpg", "600x600bb.jpg") ?: ""
    }
}