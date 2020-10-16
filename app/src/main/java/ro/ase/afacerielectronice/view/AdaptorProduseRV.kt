package ro.ase.afacerielectronice.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ro.ase.afacerielectronice.R
import ro.ase.afacerielectronice.databinding.ElemProdusRvBinding
import ro.ase.afacerielectronice.model.clase.Produs

class AdaptorProduseRV(val listaProduse: List<Produs>, val context: Context, val activity: MainActivity) :
    RecyclerView.Adapter<AdaptorProduseRV.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ElemProdusRvBinding =
            ElemProdusRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listaProduse.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val produsCurent: Produs = listaProduse.get(holder.adapterPosition)

        holder.binding.tvNume.text = produsCurent.nume.toString()
        holder.binding.tvCantitate.text = "Cantitate ramasa: ${produsCurent.cantitate}"
        holder.binding.tvPret.text = "Pret: ${produsCurent.pret.toString()}"
        holder.binding.tvDescriere.text = produsCurent.descriere.toString()

        holder.binding.ivProdus.setImageDrawable(
            when (produsCurent.id) {
                1 -> context.resources.getDrawable(R.drawable.samsung)
                2 -> context.resources.getDrawable(R.drawable.lg)
                3 -> context.resources.getDrawable(R.drawable.hisense)
                4 -> context.resources.getDrawable(R.drawable.allview)
                5 -> context.resources.getDrawable(R.drawable.phillips)
                else -> null
            }
        )

        holder.binding.ivMinus.setOnClickListener {
            if (holder.binding.tvCantitateCurenta.text.toString().toInt() > 0)
                holder.binding.tvCantitateCurenta.text =
                    "${holder.binding.tvCantitateCurenta.text.toString().toInt() - 1}"
        }

        holder.binding.ivPlus.setOnClickListener {
            if (holder.binding.tvCantitateCurenta.text.toString().toInt() < produsCurent.cantitate)
                holder.binding.tvCantitateCurenta.text =
                    "${holder.binding.tvCantitateCurenta.text.toString().toInt() + 1}"
        }

        holder.binding.btnAddToCart.setOnClickListener{
            activity.addToCart(produsCurent, holder.binding.tvCantitateCurenta.text.toString().toInt())
        }

    }

    class ViewHolder(val binding: ElemProdusRvBinding) : RecyclerView.ViewHolder(binding.root)
}