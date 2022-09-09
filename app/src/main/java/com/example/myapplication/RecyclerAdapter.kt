package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication.model.Pompa
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.File
import kotlin.coroutines.coroutineContext


class RecyclerAdapter (private val pompa: List<Pompa>): RecyclerView.Adapter<PompaHolder>(){

    var onItemClick: ((Pompa) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PompaHolder {
        return PompaHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: PompaHolder, position: Int) {
        holder.bindPompa(pompa[position])

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(pompa[position])

        }
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
    private val itemImage: ImageView = itemView.findViewById(R.id.iv_pompa)
    private lateinit var itemKeterangan: String
    private lateinit var itemId: String
    private lateinit var itemImageURL: String
    private lateinit var itemImageString: String

    fun bindPompa(pompa: Pompa){
        itemLokasi.text = pompa.lokasi
        itemJenis.text = pompa.jenis
        itemKapasitas.text = pompa.kapasitas
        itemSatuan.text = pompa.satuan
        itemKondisi.text = pompa.status
        itemKeterangan = pompa.keterangan
        itemId = pompa.id
        itemImageURL = pompa.image
        itemImageString = pompa.imagename
        val storageReference = FirebaseStorage.getInstance().reference.child("image/$itemImageURL")
        val localFile = File.createTempFile("tempImages", "jpg")
        if(itemImageURL != ""){
            Glide.with(itemView.context)
                .load(itemImageURL)
                .into(itemImage)
//            storageReference.getFile(localFile).addOnSuccessListener {
//                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
//                itemImage.setImageBitmap(bitmap)
//            }
        }
    }
}
