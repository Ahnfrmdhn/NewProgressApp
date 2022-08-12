package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.myapplication.model.User
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : BaseActivity() {
    private lateinit var userDetails: User
    lateinit var sharedPref: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        sharedPref = PreferenceHelper(this)

        val btnSummary = findViewById<LinearLayout>(R.id.ll_summary)
        val btnList = findViewById<LinearLayout>(R.id.ll_list_data)
        val btnSearchJenis = findViewById<LinearLayout>(R.id.ll_search_jenis)
        val btnSetting = findViewById<ImageView>(R.id.btn_setting)
        val btnSearchLokasi = findViewById<LinearLayout>(R.id.ll_search_lokasi)
        btnSummary.setOnClickListener{
            val intent = Intent(this, SummaryActivity::class.java)
            startActivity(intent)
        }

        btnList.setOnClickListener{
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }

        btnSearchJenis.setOnClickListener{
            val intent = Intent(this, SearchPompaActivity::class.java)
            startActivity(intent)
        }
        btnSearchLokasi.setOnClickListener{
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        btnSetting.setOnClickListener{
            readDetailData()
        }

    }

    fun readDetailData(){
        userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        val user: User = userDetails
        val intent = Intent(this, UserProfileActivity::class.java)
        intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
        sharedPref.put(Constants.PREF_USERNAME, user.email)
        sharedPref.put(Constants.PREF_POSISI, user.position)
        sharedPref.put(Constants.PREF_NAME, user.name)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

    }
}