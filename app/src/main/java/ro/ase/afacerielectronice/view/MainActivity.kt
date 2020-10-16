package ro.ase.afacerielectronice.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import retrofit2.Response
import ro.ase.afacerielectronice.R
import ro.ase.afacerielectronice.databinding.ActivityMainBinding
import ro.ase.afacerielectronice.model.clase.Produs
import ro.ase.afacerielectronice.viewmodel.ProduseViewModel
import java.io.Serializable


class MainActivity : AppCompatActivity() {

    lateinit var produseViewModel: ProduseViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var adaptorProduseRV: AdaptorProduseRV

    lateinit var mapProduse: MutableMap<Produs, Int>

    lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        snackbar = Snackbar.make(binding.root, "Added to cart", Snackbar.LENGTH_LONG).setAction("OK", null)

        mapProduse = mutableMapOf()

        binding.rvProduse.layoutManager = LinearLayoutManager(applicationContext)

        produseViewModel = ViewModelProvider(this).get(ProduseViewModel::class.java)

        produseViewModel.getProduse().observe(this, Observer<Response<List<Produs>>> { response ->
            if (response.body() != null) {
                adaptorProduseRV = AdaptorProduseRV(response.body()!!, applicationContext, this)
                binding.rvProduse.adapter = adaptorProduseRV
            } else
                Toast.makeText(applicationContext, response.message(), Toast.LENGTH_SHORT).show()
        })

        produseViewModel.getIsLoading().observe(this, Observer<Boolean> { response ->
            binding.srlProduse.isRefreshing = response
        })

        binding.srlProduse.setOnRefreshListener {
            produseViewModel.getProduse()
                .observe(this, Observer<Response<List<Produs>>> { response ->
                    if (response.body() != null) {

                        adaptorProduseRV =
                            AdaptorProduseRV(response.body()!!, applicationContext, this)
                        binding.rvProduse.adapter = adaptorProduseRV
                    } else
                        Toast.makeText(applicationContext, response.message(), Toast.LENGTH_SHORT)
                            .show()
                })
        }

        binding.fabCart.setOnClickListener {
            if (mapProduse.size != 0)
                startActivityForResult(Intent(applicationContext, CartActivity::class.java).putExtra("map", mapProduse as Serializable),1)
            else
                Toast.makeText(applicationContext, "Cart is empty", Toast.LENGTH_SHORT).show()
        }

    }

    fun addToCart(produs: Produs, cantitate: Int) {
        if (cantitate == 0) {
            mapProduse.remove(produs)
            return
        }
        snackbar.show()
        mapProduse.remove(produs)
        mapProduse.put(produs, cantitate)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1&&resultCode==1) {
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
            mapProduse.clear()
        }

    }
}