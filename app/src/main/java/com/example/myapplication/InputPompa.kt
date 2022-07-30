package com.example.myapplication

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore

class InputPompa : AppCompatActivity() {

    private lateinit var etKapasitas: TextInputEditText
    private lateinit var etKet: TextInputEditText

    private lateinit var database: DatabaseReference

    private lateinit var txtLokasi: String
    private lateinit var txtJenis: String
    private lateinit var txtSatuan: String
    private lateinit var txtStatus: String
    private lateinit var txtKet: String


    var db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_pompa)

        val spPosisi = findViewById<Spinner>(R.id.sp_lokasi)
        val spJenis = findViewById<Spinner>(R.id.sp_jenis)
        val spSatuan = findViewById<Spinner>(R.id.sp_satuan)
        val spStatus = findViewById<Spinner>(R.id.sp_status)
        etKapasitas = findViewById(R.id.et_kapasitas)
        etKet = findViewById(R.id.et_ket)

        spPosisi.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val textP = parent?.getItemAtPosition(position)
                txtLokasi = textP.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        spJenis.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val textJ = parent?.getItemAtPosition(position)
                txtJenis = textJ.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        spSatuan.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val textS = parent?.getItemAtPosition(position)
                txtSatuan = textS.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        spStatus.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val textSt = parent?.getItemAtPosition(position)
                txtStatus = textSt.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        val btnSave = findViewById<LinearLayout>(R.id.btn_simpan)
        btnSave.setOnClickListener{
            val etKap: String = etKapasitas.text.toString()
            val etKet: String = etKet.text.toString()
            saveData(txtLokasi, txtJenis, etKap, txtSatuan, txtStatus, etKet)
        }

    }

    fun saveData(lokasi: String, jenis: String, kapasitas: String, satuan: String, status: String, keterangan: String){
        val pompa: MutableMap<String, Any> = HashMap()

        pompa["lokasi"] = lokasi
        pompa["jenis"] = jenis
        pompa["kapasitas"] = kapasitas
        pompa["satuan"] = satuan
        pompa["status"] = status
        pompa["keterangan"] = keterangan

        if (kapasitas.isEmpty()){
            etKapasitas.error
            return
        }

        db.collection("pompa")
            .add(pompa)
            .addOnSuccessListener {
                Toast.makeText(this@InputPompa, "Data pompa berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this@InputPompa, "Gagal ditambahkan", Toast.LENGTH_SHORT).show()
            }
    }
}