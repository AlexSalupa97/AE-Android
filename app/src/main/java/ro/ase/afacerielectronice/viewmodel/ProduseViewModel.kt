package ro.ase.afacerielectronice.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.Response
import ro.ase.afacerielectronice.model.clase.Produs
import ro.ase.afacerielectronice.model.repo.RepoProduse

class ProduseViewModel(application: Application) : AndroidViewModel(application) {

    val repoProduse: RepoProduse = RepoProduse()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Toast.makeText(getApplication(), throwable.message, Toast.LENGTH_SHORT).show()
        throwable.printStackTrace()
    }

    fun getIsLoading() = isLoading


    fun getProduse(): LiveData<Response<List<Produs>>> =

        liveData(exceptionHandler) {
            isLoading.postValue(true)
            val response: Response<List<Produs>> = repoProduse.getProduse()
            emit(response)
            isLoading.postValue(false)
        }


}