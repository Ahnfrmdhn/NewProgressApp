package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.myapplication.model.Pompa
import com.example.myapplication.utils.GlideLoader
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.IOException
import java.util.*

class EditPompa : AppCompatActivity() {

    private lateinit var pompa: Pompa

    private lateinit var idPompa: String
    private lateinit var txtLokasi: String
    private lateinit var txtJenis: String
    private lateinit var txtSatuan: String
    private lateinit var txtStatus: String
    private lateinit var imageURL: String

    private var clickChecker: Int = 0

    private var selectedImageFileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pompa)

        pompa = intent.getParcelableExtra<Pompa>("pompa")!!

        val spLokasi = findViewById<Spinner>(R.id.sp_lokasi)
        val spJenis = findViewById<Spinner>(R.id.sp_jenis)
        val spSatuan = findViewById<Spinner>(R.id.sp_satuan)
        val spStatus = findViewById<Spinner>(R.id.sp_status)
        val tvKap = findViewById<TextView>(R.id.et_kapasitas)
        val tvKet = findViewById<TextView>(R.id.et_ket)
        val ivPompa = findViewById<ImageView>(R.id.iv_pompa)

        ivPompa.setOnClickListener {
            clickChecker = 1
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED){
//                showErrorSnackBar("You already have the storge permission", false)
                //intent to gallery
                Constants.showImageChooser(this)
            }else{
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    Constants.READ_STORAGE_PERMISSION_CODE
                )
            }
        }

        idPompa = pompa.id
        spLokasi.setSelection(getIndex(resources.getStringArray(R.array.location), pompa.lokasi))
        spJenis.setSelection(getIndex(resources.getStringArray(R.array.jenispompa), pompa.jenis))
        spSatuan.setSelection(getIndex(resources.getStringArray(R.array.satuan), pompa.satuan))
        tvKap.text = pompa.kapasitas
        tvKet.text = pompa.keterangan
        imageURL = pompa.image
        if (pompa.image != ""){
            Glide.with(this)
                .load(pompa.image)
                .into(ivPompa)
        }

        spJenis.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                txtJenis = parent?.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        spLokasi.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                txtLokasi = parent?.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        spSatuan.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                txtSatuan = parent?.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        spStatus.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                txtStatus = parent?.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        val btnSimpan = findViewById<LinearLayout>(R.id.btn_simpan)
        btnSimpan.setOnClickListener {
            updatePompa()
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                showErrorSnackBar("Storage permission is granted", false)
                //intent to gallery
                Constants.showImageChooser(this)
            }else{
                Toast.makeText(
                    this,
                    "Permission storage is denied. You can also allow it from settings",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val ivPhoto = findViewById<ImageView>(R.id.iv_pompa)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null){
                    try {
                        selectedImageFileUri = data.data

//                        ivPhoto.setImageURI(selectedImageFileUri)
                        GlideLoader(this).loaderPicture(selectedImageFileUri!!, ivPhoto)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this,
                            "Gagal memilih gambar",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }else if(resultCode == Activity.RESULT_CANCELED){
            Log.e("Request cancelled", "image selection cancelled")
        }
    }

//    fun setItem(spinner: Spinner) : String{
//        var data: String = ""
//        spinner.onItemSelectedListener = object :
//        AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                data = parent?.getItemAtPosition(position).toString()
//
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                TODO("Not yet implemented")
//            }
//        }
//        return data
//    }

    fun getIndex(array: Array<String>, text: String): Int{
        var result = 0
        for(i in array.indices){
            if(array[i] == text){
                result = i
            }
        }
        return result
    }

    fun updatePompa(){
        val tvKap = findViewById<TextInputEditText>(R.id.et_kapasitas)
        val tvKet = findViewById<TextInputEditText>(R.id.et_ket)
        var imageNameOnCloud: String
        var data: Pompa = Pompa("","","","","","","","")
        if (clickChecker != 0){
            val imageName = UUID.randomUUID().toString() + ".jpg"
            val ref = FirebaseStorage.getInstance().reference.child("images/$imageName")
            val uploadTask = ref.putFile(selectedImageFileUri!!)
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful){
                    imageNameOnCloud = task.result.toString()
                    data = Pompa(
                        idPompa.trim(),
                        txtLokasi.trim(),
                        txtJenis.trim(),
                        tvKap.text.toString().trim(),
                        txtSatuan.trim(),
                        txtStatus.trim(),
                        tvKet.text.toString().trim(),
                        imageNameOnCloud
                    )
                    FirebaseFirestore.getInstance().collection(Constants.POMPA)
                        .document(idPompa)
                        .set(data)
                }
            }
        }else{
            data = Pompa(
                idPompa.trim(),
                txtLokasi.trim(),
                txtJenis.trim(),
                tvKap.text.toString().trim(),
                txtSatuan.trim(),
                txtStatus.trim(),
                tvKet.text.toString().trim(),
                imageURL.trim()
            )
            FirebaseFirestore.getInstance().collection(Constants.POMPA)
                .document(idPompa)
                .set(data)
        }


    }
}