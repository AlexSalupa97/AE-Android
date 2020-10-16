package ro.ase.afacerielectronice.model.clase

import java.io.Serializable

data class Produs(
    val id: Int = 0,
    val nume: String = "",
    val pret: Int = 0,
    var cantitate: Int = 0,
    val descriere: String = ""
):Serializable