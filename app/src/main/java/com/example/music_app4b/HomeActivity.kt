package com.example.music_app4b

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.music_app4b.model.iTunesResponse
import com.example.music_app4b.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.bumptech.glide.Glide
import com.example.music_app4b.model.Album

class HomeActivity : AppCompatActivity() {

    private lateinit var tvResultado : TextView
    private lateinit var contenedorIndie : LinearLayout
    private lateinit var contenedorRegional : LinearLayout
    private lateinit var contenedorTOP : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)


        tvResultado = findViewById(R.id.tvResultado)
        contenedorIndie = findViewById(R.id.contenedorIndie)
        contenedorRegional = findViewById(R.id.contenedorRegional)
        contenedorTOP = findViewById(R.id.contenedorTOP)


        //tod: Agregar metodo de llamado de album
        obtenerAlbum("indie", contenedorIndie)
        obtenerAlbum("Regional Mexicano", contenedorRegional)
        obtenerAlbum("Grupo firme", contenedorTOP)


    }

    //Metodo para obtener informacion del album
    private fun obtenerAlbum(genero : String, contenedor : LinearLayout){

        //Hacer el llamado a la construccion de la URL
        RetrofitClient.api.obtenerAlbumGenero(
            genero = genero,
            pais = "MX",
            media = "music",
            entidad = "album",
            limite = 10
        ).enqueue(object : Callback<iTunesResponse> {

            //Metodo en caso de que la respuesta sea correcta
            override fun onResponse(call: Call<iTunesResponse?>, response: Response<iTunesResponse?>) {

                //Variable para guardar el numero de albunes consultados
                // Variable que guarda la lista de tipo album
                val albumes = response.body()?.results ?: emptyList()

                //Validar si las variables albumes tiene informacion
                if(albumes.isNotEmpty()){

                    tvResultado.visibility = View.GONE
                    mostrarAlbumes(albumes, contenedor)

                }else{
                    Toast.makeText(this@HomeActivity, "No hay respuesta del API", Toast.LENGTH_SHORT).show()
                }


            }

            //Metodo para hacer una accion si el api falla
            override fun onFailure(call: Call<iTunesResponse?>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Error en el API", Toast.LENGTH_SHORT).show()
            }

        } )

    }

    //Metodo para mostrar el listado de datos de un API
    private fun mostrarAlbumes(albumes: List<Album>, contenedor : LinearLayout){

        //OBLIGATORIO: Evitar elementos duplicados
        contenedor.removeAllViews()

        //Ciclo para recorrer las multiples peticiones peticiones a una API
        for (album in albumes){

            //Usar el Linearyout con el contenedor
            val vistaAlbum = layoutInflater.inflate(
                R.layout.item_album,
                contenedor,
                false
            )

            val imgPortada = vistaAlbum.findViewById<ImageView>(R.id.imgPortadaAlbum)
            val tvNombreAlbum = vistaAlbum.findViewById<TextView>(R.id.tvNombreAlbum)
            val tvNombreArtista = vistaAlbum.findViewById<TextView>(R.id.tvNombreArtista)

            // Asignando los nombres obtenidos del API
            tvNombreAlbum.text = album.collectionName ?: "Album sin nombre"
            tvNombreArtista.text = album.artistName ?: "Artita desconocido"

            // MOSTRAR LA IMAGEN EN LA TARJETA
            Glide.with(this@HomeActivity)
                .load(album.obtenerImagenGrande())
                .into(imgPortada)

            // Asignar la vista del contenedor a las tarjetas de albumes
            contenedor.addView(vistaAlbum)

        }



    }

}