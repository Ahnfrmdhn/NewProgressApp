package com.example.myapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.model.Pompa
import com.example.myapplication.model.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class DetailActivity : BaseActivity() {

    private var backPressTime = 0L


    private lateinit var userDetails: User

    private val firebaseRepo: FirebaseRepo = FirebaseRepo()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val fab = findViewById<FloatingActionButton>(R.id.fab_add)
        fab.setOnClickListener{
            startActivity(Intent(this@DetailActivity, InputPompa::class.java))
        }

        FirestroreClass().getUserDetails(this@DetailActivity)
        val btnDownload = findViewById<ImageView>(R.id.btn_download)

        btnDownload.setOnClickListener {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PackageManager.PERMISSION_GRANTED)
            ExcelHelper().excelCreater()
        }

        readDataPompa()
        iniRefreshListener()

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("EXIT", true)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    fun readDetailData(){
        userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!

        val user: User = userDetails
        val intent = Intent(this@DetailActivity, UserProfileActivity::class.java)
        intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
        startActivity(intent)
    }

    fun iniRefreshListener() {
        val swipeRefreshLayout=findViewById<SwipeRefreshLayout>(R.id.swipe)
        swipeRefreshLayout.setOnRefreshListener{
            readDataPompa()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun readDataPompa() {
        var i = 0
        val db = FirebaseFirestore.getInstance()
        db.collection(Constants.POMPA)
            .get()
            .addOnSuccessListener {
                var listPompa: ArrayList<Pompa> = ArrayList()

                for (document in it){
                    if(document.data.get("lokasi") as String == "utara"){
                        i++
                    }
                    listPompa.add((Pompa(
                        document.id as String,
                        document.data.get("lokasi") as String,
                        document.data.get("jenis") as String,
                        document.data.get("kapasitas") as String,
                        document.data.get("satuan") as String,
                        document.data.get("status") as String,
                        document.data.get("keterangan") as String,
                        document.data.get("image") as String,
                        document.data.get("imagename") as String
                    )))
                }

                var pompaAdapter = RecyclerAdapter(listPompa.sortedBy { it.lokasi })
                var rvPompa = findViewById<RecyclerView>(R.id.rv_pompa)


                pompaAdapter.onItemClick = {
                    val intent = Intent(this@DetailActivity, DetailPompaActivity::class.java)
                    intent.putExtra("pompa", it)
                    startActivity(intent)
                }

                rvPompa.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = pompaAdapter
                }


            }

    }



}