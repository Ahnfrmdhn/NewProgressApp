package com.example.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.adapter.PompaAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : BaseActivity() {

    var db = FirebaseFirestore.getInstance()

    private val firebaseRepo: FirebaseRepo = FirebaseRepo()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab_add)
        fab.setOnClickListener{
            startActivity(Intent(this@MainActivity, InputPompa::class.java))
        }

        FirestroreClass().getUserDetails(this@MainActivity)
        val btnSetting = findViewById<ImageView>(R.id.btn_setting)
        btnSetting.setOnClickListener {
            startActivity(Intent(this@MainActivity, UserProfileActivity::class.java).putExtra(Constants.EXTRA_USER_DETAILS, User()))
        }

        readDataPompa()
        iniRefreshListener()

    }

    fun iniRefreshListener() {
        val swipeRefreshLayout=findViewById<SwipeRefreshLayout>(R.id.swipe)
        swipeRefreshLayout.setOnRefreshListener{
            readDataPompa()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun readDataPompa() {
        db.collection("pompa")
            .get()
            .addOnSuccessListener {
                var listPompa: ArrayList<Pompa> = ArrayList()

                for (document in it){
                    listPompa.add((Pompa(
                        document.id as String,
                        document.data.get("lokasi") as String,
                        document.data.get("jenis") as String,
                        document.data.get("kapasitas") as String,
                        document.data.get("satuan") as String,
                        document.data.get("status") as String,
                        document.data.get("keterangan") as String
                    )))
                }

                var pompaAdapter = RecyclerAdapter(listPompa)
                var rvPompa = findViewById<RecyclerView>(R.id.rv_pompa)

                rvPompa.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = pompaAdapter
                }
            }

    }


}