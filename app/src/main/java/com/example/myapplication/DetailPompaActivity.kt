package com.example.myapplication

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.myapplication.model.Pompa
import com.example.myapplication.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DetailPompaActivity : AppCompatActivity() {

    private var db = FirebaseFirestore.getInstance()
    private lateinit var pompa: Pompa
    private lateinit var getId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pompa)

        readDetailData()


        val btnDelete = findViewById<Button>(R.id.btn_hapus)
        btnDelete.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Menghapus data")
            builder.setMessage("Apakah yakin akan menghapus data?")
            builder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i -> deleteData() })
            val negativeButtonClicked = {dialog: DialogInterface, which: Int ->
                Toast.makeText(applicationContext, "Data tidak dihapus", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Tidak",negativeButtonClicked)
            builder.show()
        }

        val btnEdit = findViewById<Button>(R.id.btn_edit)
        btnEdit.setOnClickListener{
            val intent = Intent(this, EditPompa::class.java)
            intent.putExtra("pompa", pompa)
            startActivity(intent)


        }

    }

    fun readDetailData(){

        pompa = intent.getParcelableExtra<Pompa>("pompa")!!
        if (pompa != null){
            val tvLokasi: TextView = findViewById(R.id.tv_lokasi)
            val tvJenis: TextView = findViewById(R.id.tv_jenis)
            val tvKapasitas: TextView = findViewById(R.id.tv_kapasitas)
            val tvSatuan: TextView = findViewById(R.id.tv_satuan)
            val tvStatus: TextView =findViewById(R.id.tv_status)
            val tvKet: TextView = findViewById(R.id.tv_keterangan)
            val ivPompa: ImageView = findViewById(R.id.img_detail)

            tvLokasi.text = pompa.lokasi
            tvJenis.text = pompa.jenis
            tvKapasitas.text = pompa.kapasitas
            tvSatuan.text = pompa.satuan
            tvStatus.text = pompa.status
            tvKet.text = pompa.keterangan
            if (pompa.image != ""){
                Glide.with(this)
                    .load(pompa.image)
                    .into(ivPompa)
            }
            getId = pompa.id
            val warna:String = pompa.status
            if(warna != "mati"){
                tvStatus.setTextColor(ContextCompat.getColor(baseContext, R.color.colorSnackBarSuccess))
            }else{
                tvStatus.setTextColor(ContextCompat.getColor(baseContext, R.color.colorSnackBarError))
            }
        }
    }

    fun deleteData(){

        db.collection("pompa")
            .document(getId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "berhasil menghapus", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DetailActivity::class.java))
            }

    }

    fun editData(){

    }
}