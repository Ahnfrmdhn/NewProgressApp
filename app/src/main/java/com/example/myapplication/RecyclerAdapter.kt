package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView


class RecyclerAdapter (private val pompa: List<Pompa>): RecyclerView.Adapter<PompaHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PompaHolder {
        return PompaHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: PompaHolder, position: Int) {
        holder.bindPompa(pompa[position])
    }

    override fun getItemCount(): Int {
        return pompa.size
    }

}

class PompaHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
    private val itemLokasi: TextView = itemView.findViewById(R.id.tv_lokasi)
    private val itemJenis: TextView = itemView.findViewById(R.id.tv_jenis)
    private val itemKapasitas: TextView = itemView.findViewById(R.id.tv_kapasitas)
    private val itemSatuan: TextView = itemView.findViewById(R.id.tv_satuan)
    private val itemKondisi: TextView = itemView.findViewById(R.id.tv_kondisi)

    fun bindPompa(pompa: Pompa){
        itemLokasi.text = pompa.lokasi
        itemJenis.text = pompa.jenis
        itemKapasitas.text = pompa.kapasitas
        itemSatuan.text = pompa.satuan
        itemKondisi.text = pompa.status
    }
}
