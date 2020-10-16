package ro.ase.afacerielectronice.model.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import ro.ase.afacerielectronice.model.clase.Produs

interface ProduseRS {

    @GET("domain.produse")
    suspend fun getProduse(): Response<List<Produs>>

    @PUT("domain.produse/{id}")
    fun putProduse(@Path("id") id: Int, @Body produs: Produs): Call<Produs>

    @PUT("domain.produse/{id}/{cantitate}")
    fun putProduse(@Path("id") id: Int, @Path("cantitate") cantitate: Int, @Body produs: Produs): Call<Produs>
}