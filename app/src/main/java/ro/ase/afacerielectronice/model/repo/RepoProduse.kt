package ro.ase.afacerielectronice.model.repo

import ro.ase.afacerielectronice.model.retrofit.ClientRetrofit

class RepoProduse {

    var clientRetrofit = ClientRetrofit.clientCoroutines

    suspend fun getProduse() = clientRetrofit.getProduse()
}