package com.example.music_app4b.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//ARCHIVO QUE PERMITE TENER UN OBJETO PARA LA CONEXION A INTERNET

object RetrofitClient {

    //Constante para tener la Base URL
    private const val BASE_URL = "https://itunes.apple.com/"

    //Variable que hereda de "lazy"
    val api : ITunesApi by lazy {
        //Construir la url completa
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesApi::class.java)
    }

}