package ro.ase.afacerielectronice.model.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import ro.ase.afacerielectronice.model.clase.Produs

object ClientRetrofit {

    val clientCoroutines by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.0.196:8080/AfaceriElectronice/webresources/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(ProduseRS::class.java)
    }
}