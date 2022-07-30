package com.example.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class UserProfileActivity : BaseActivity() {
    private var auth = FirebaseAuth.getInstance()
    private var db = FirebaseFirestore.getInstance()

    private lateinit var result: String
    private lateinit var userDetails: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val sharedPreferences = getSharedPreferences(Constants.MYSHOPPAL_PREFERENCES, Context.MODE_PRIVATE)
        val username = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME,"")!!

        val etNama = findViewById<TextView>(R.id.et_name)
        val etEmail = findViewById<TextView>(R.id.et_email)
        val tvJabatan = findViewById<TextView>(R.id.tv_posisi)

        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)){
            userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

        etNama.text = userDetails.name
        etEmail.text = userDetails.email
        tvJabatan.text = userDetails.position

        val btnSave = findViewById<Button>(R.id.btn_save)
        btnSave.setOnClickListener {
            if (validateProfile()){
                showErrorSnackBar("Data sudah benar", false)
//                updateUser()
                showProgressDialog("Tunggu Sebentar")
                hideProgressDialog()

            }
        }

        val spinner = findViewById<Spinner>(R.id.sp_position)
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                result = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun validateProfile(): Boolean{
        val etName = findViewById<TextInputEditText>(R.id.et_name)
        val etMail = findViewById<TextInputEditText>(R.id.et_email)

        return when {
            TextUtils.isEmpty(etName.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_name), true)
                false
            }
            TextUtils.isEmpty(etMail.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_mail), true)
                false
            }

            else -> {
                //showErrorSnackBar("Benar.", false)
                true
            }
        }
    }

//    private fun updateUser(){
//        val etName = findViewById<TextInputEditText>(R.id.et_name)
//        val etMail = findViewById<TextInputEditText>(R.id.et_email)
//
//        val rootRef = FirebaseFirestore.getInstance()
//        val userRef = rootRef.collection(Constants.USERS)
//
//        if(validateProfile()) {
//            FirebaseAuth.getInstance().currentUser?.apply {
//
//                val user = User(
//                    etName.text.toString().trim{it <= ' '},
//                    etMail.text.toString().trim { it <= ' ' },
//                    result.trim{ it <= ' '}
//                )
//
//                FirestroreClass().updateProfile(this@UserProfileActivity, user)
//                hideProgressDialog()
//            }
//        }
//    }

}