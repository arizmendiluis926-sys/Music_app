package com.example.music_app4b.network

import com.example.music_app4b.model.iTunesResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Archivos para la consulta de información (local o internet)

interface ITunesApi {

    // Indicar el método HTTP a usar
    @GET("search")

    // Metodo para obtener la informacion con la url
    fun obtenerAlbumGenero(
        @Query("term") genero: String,
        @Query("country") pais : String,
        @Query("media") media : String,
        @Query("entity") entidad : String,
        @Query("Limit") limite : Int,
    ) : retrofit2.Call<iTunesResponse>
}