package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.model.Pompa
import com.example.myapplication.utils.GlideLoader
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.HashMap

class InputPompa : BaseActivity(){

    private lateinit var etKapasitas: TextInputEditText
    private lateinit var etKet: TextInputEditText

    private lateinit var database: DatabaseReference

    private lateinit var txtLokasi: String
    private lateinit var txtJenis: String
    private lateinit var txtSatuan: String
    private lateinit var txtStatus: String
    private lateinit var txtKet: String
    private var imageBitmap: Bitmap? = null

//    private lateinit var imageNameOnCloud: String

    private var selectedImageFileUri: Uri? = null

    private var storageReference: StorageReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_pompa)

        val spPosisi = findViewById<Spinner>(R.id.sp_lokasi)
        val spJenis = findViewById<Spinner>(R.id.sp_jenis)
        val spSatuan = findViewById<Spinner>(R.id.sp_satuan)
        val spStatus = findViewById<Spinner>(R.id.sp_status)
        etKapasitas = findViewById(R.id.et_kapasitas)
        etKet = findViewById(R.id.et_ket)

        storageReference = FirebaseStorage.getInstance().reference

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



        /*btnSave.setOnClickListener{
            val etKap: String = etKapasitas.text.toString()
            val etKet: String = etKet.text.toString()
            var imageNameOnCloud: String

            if (selectedImageFileUri != null){
                if(selectedImageFileUri != null){
                    val imageName = UUID.randomUUID().toString() + ".jpg"
                    val imageNameOnFirestore = imageName
                    val ref = storageReference!!.child("images/$imageName")
                    val uploadTask = ref.putFile(selectedImageFileUri!!)
                    uploadTask.continueWithTask{ task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }
                        ref.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            imageNameOnCloud = task.result.toString()
                            Toast.makeText(this, imageNameOnCloud, Toast.LENGTH_LONG).show()
                            saveData(txtLokasi, txtJenis, etKap, txtSatuan, txtStatus, etKet, imageNameOnCloud, imageNameOnFirestore)
                        }
                    }
                }else{
                    Toast.makeText(
                        this,
                        "Pilih gambar",
                        Toast.LENGTH_LONG
                    ).show()
                }
//                FirestroreClass().uploadImageToCloudeStorage(this, selectedImageFileUri)
            }else{
                Toast.makeText(this, "Gambar belum ditambahkan", Toast.LENGTH_SHORT).show()
            }
            finish()

        }*/

        btnSave.setOnClickListener{
            if (imageBitmap == null){
                Toast.makeText(
                    this,
                    "gambar belum dipilih",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                uploadImage(imageBitmap!!)
                startActivity(Intent(this, DetailActivity::class.java))
                finish()
            }

        }

        val btnCancel = findViewById<LinearLayout>(R.id.btn_cancel)
        btnCancel!!.setOnClickListener({
            onBackPressed()
        })

        val ivPhoto = findViewById<ImageView>(R.id.img_pompa)
        ivPhoto.setOnClickListener {
            Constants.goToCamera(this)
        }
        /*
        ivPhoto.setOnClickListener{
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
        */

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Constants.goToCamera(this)
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
        val ivPhoto = findViewById<ImageView>(R.id.img_pompa)
        if (requestCode == Constants.REQ_CAM && resultCode == Activity.RESULT_OK){
            imageBitmap = data?.extras?.get("data") as Bitmap
            ivPhoto.setImageBitmap(imageBitmap)
        }else{
            Toast.makeText(
                this,
                "Gambar belum dipilih",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun uploadImage(imageBitmap: Bitmap) {

        val etKap: String = etKapasitas.text.toString()
        val etKet: String = etKet.text.toString()

        val baos = ByteArrayOutputStream()
        val imageName = UUID.randomUUID().toString() + ".jpg"
        val imageNameOnFirestore = imageName
        var imageNameOnCloud: String = ""
        val ref = storageReference!!.child("images/$imageName")
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos)

        val img = baos.toByteArray()
        ref.putBytes(img)
            .continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref.downloadUrl
            }
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    imageNameOnCloud = task.result.toString()
                    Toast.makeText(this, imageNameOnCloud, Toast.LENGTH_LONG).show()
                    saveData(txtLokasi, txtJenis, etKap, txtSatuan, txtStatus, etKet, imageNameOnCloud, imageNameOnFirestore)
                }
            }
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val ivPhoto = findViewById<ImageView>(R.id.img_pompa)
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
    }*/

    fun saveData(lokasi: String, jenis: String, kapasitas: String, satuan: String, status: String, keterangan: String, imageUrl: String, imageString: String){
        val pompa: MutableMap<String, Any> = HashMap()

        pompa["lokasi"] = lokasi
        pompa["jenis"] = jenis
        pompa["kapasitas"] = kapasitas
        pompa["satuan"] = satuan
        pompa["status"] = status
        pompa["keterangan"] = keterangan
        pompa["image"] = imageUrl
        pompa["imagename"] = imageString

        if (kapasitas.isEmpty()){
            etKapasitas.error
            return
        }

        FirebaseFirestore.getInstance().collection(Constants.POMPA)
            .add(pompa)
            .addOnSuccessListener {
                Toast.makeText(this@InputPompa, "Data pompa berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this@InputPompa, "Gagal ditambahkan", Toast.LENGTH_SHORT).show()
            }
    }

}