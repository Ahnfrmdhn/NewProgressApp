package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.myapplication.Pompa
import com.example.myapplication.R
import org.w3c.dom.Text

class PompaAdapter (val pCtx: Context, val layoutResId: Int, val itemList: List<Pompa>): ArrayAdapter<Pompa>(pCtx, layoutResId, itemList) {


        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val layoutInflater: LayoutInflater = LayoutInflater.from(pCtx)

                val view: View = layoutInflater.inflate(layoutResId, null)

                val tvLokasi: TextView = view.findViewById(R.id.tv_lokasi)
                val tvJenis: TextView = view.findViewById(R.id.tv_jenis)
                val tvKapasitas: TextView = view.findViewById(R.id.tv_kapasitas)
                val tvSatuan: TextView = view.findViewById(R.id.tv_satuan)
                val tvKondisi: TextView = view.findViewById(R.id.tv_kondisi)

                val item = itemList[position]

                tvLokasi.text = item.lokasi
                tvJenis.text = item.jenis
                tvKapasitas.text = item.kapasitas
                tvSatuan.text = item.satuan
                tvKondisi.text = item.status

                return view
        }
}