package com.example.projetrp.ui.loi

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projetrp.R
import com.example.projetrp.model.Loi
import kotlinx.android.synthetic.main.list_loi.view.*

class LoiRecyclerAdapter(
    private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var lois: List<Loi> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LoiViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_loi, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return lois.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is LoiViewHolder -> {
                holder.bind(lois[position], cellClickListener)
            }
        }
    }

    fun submitList(lisLois: List<Loi>){
        lois = lisLois
    }

    class LoiViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        val title = itemView.title
        val subtitle = itemView.subtitle
        val listLoi = itemView.item_loi
        fun bind(loi: Loi, clickListener: CellClickListener){
            title.setText(loi.title)
            subtitle.setText(loi.subtitle)
            listLoi.setOnClickListener {
                clickListener.onCellClicked(loi)
            }
        }
    }
}