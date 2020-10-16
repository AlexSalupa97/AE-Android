package ro.ase.afacerielectronice.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ro.ase.afacerielectronice.R
import ro.ase.afacerielectronice.databinding.ActivityCartBinding
import ro.ase.afacerielectronice.databinding.ActivityMainBinding
import ro.ase.afacerielectronice.model.clase.Produs
import ro.ase.afacerielectronice.model.retrofit.ClientRetrofit

class CartActivity : AppCompatActivity() {

    lateinit var binding: ActivityCartBinding
    var mapProduseCart: Map<Produs, Int>? = null
    var sumaCart: Int = 0
    var clientRetrofit = ClientRetrofit.clientCoroutines

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapProduseCart = intent.extras?.get("map") as? Map<Produs, Int>

        val listaProduse: MutableList<String> = mutableListOf()
        for ((produs, cantitate) in mapProduseCart?.entries!!) {
            listaProduse.add("${produs.nume}: ${cantitate} buc.")
            sumaCart += produs.pret * cantitate
        }

        val adaptor =
            ArrayAdapter(applicationContext, R.layout.elem_produs_lv, listaProduse)

        binding.lvProduseCart.adapter = adaptor

        binding.tvPretTotal.text = "Pret total: $sumaCart"

        binding.fabPlata.setOnClickListener {
            for ((produs, cantitate) in mapProduseCart?.entries!!) {

                clientRetrofit.putProduse(produs.id, cantitate, produs).enqueue(
                    object : Callback<Produs> {
                        override fun onFailure(call: Call<Produs>, t: Throwable) {
                            Toast.makeText(applicationContext,t.message,Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<Produs>, response: Response<Produs>) {
                            Toast.makeText(applicationContext,"Ati cumparat $cantitate ${produs.nume}",Toast.LENGTH_SHORT).show()
                            setResult(1)
                            finish()
                        }
                    }
                )
            }



        }


    }
}