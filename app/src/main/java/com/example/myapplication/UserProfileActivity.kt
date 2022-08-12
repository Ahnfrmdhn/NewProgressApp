package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.example.myapplication.model.User
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import io.grpc.InternalChannelz.id
import org.w3c.dom.Text

class UserProfileActivity : BaseActivity() {
    private var auth = FirebaseAuth.getInstance()

    private lateinit var result: String
    private lateinit var userDetails: User
    private lateinit var idUser: String

    lateinit var sharedPref: PreferenceHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val sharedPreferences = getSharedPreferences(Constants.MYSHOPPAL_PREFERENCES, Context.MODE_PRIVATE)
        val useremail = sharedPreferences.getString(Constants.PREF_USERNAME,"")!!

        sharedPref = PreferenceHelper(this)

        val btnLogout = findViewById<Button>(R.id.btn_logout)
        val etNama = findViewById<TextView>(R.id.et_name)
        val etEmail = findViewById<TextView>(R.id.et_email)
        val tvJabatan = findViewById<TextView>(R.id.tv_posisi)
        val spPosisi = findViewById<Spinner>(R.id.sp_position)

        btnLogout.setOnClickListener {
//            sharedPref.clear()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)){
            userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

//        idUser = sharedPref.getString(Constants.PREF_ID).toString()
//        etNama.text = sharedPref.getString(Constants.PREF_NAME)
//        etEmail.text = sharedPref.getString(Constants.PREF_USERNAME)
//        spPosisi.setSelection(EditPompa().getIndex(resources.getStringArray(R.array.position), sharedPref.getString(Constants.PREF_POSISI).toString()))

        idUser = userDetails.id
        etNama.text = userDetails.name
        etEmail.text = userDetails.email
        spPosisi.setSelection(EditPompa().getIndex(resources.getStringArray(R.array.position), userDetails.position))


        val btnSave = findViewById<Button>(R.id.btn_save)
        btnSave.setOnClickListener {
            if (validateProfile()){
                showErrorSnackBar("Data sudah diperbaharui", false)
                upDateUser()
                showProgressDialog("Tunggu Sebentar")
                hideProgressDialog()
            }
        }


        spPosisi.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                result = parent?.getItemAtPosition(position).toString()
                tvJabatan.text = result
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

    fun upDateUser(){
        val etName = findViewById<TextInputEditText>(R.id.et_name)
        val etMail = findViewById<TextInputEditText>(R.id.et_email)
        val data = User(
            idUser.trim(),
            etName.text.toString().trim(),
            etMail.text.toString().trim(),
            "",
            result.trim(),
            0
        )
        if(validateProfile()){
            FirebaseFirestore.getInstance().collection(Constants.USERS)
                .document(idUser)
                .set(data)

        }
    }

//    private fun afterLogin(){
//        val etNama = findViewById<TextView>(R.id.et_name)
//        val etEmail = findViewById<TextView>(R.id.et_email)
//        val spPosisi = findViewById<Spinner>(R.id.sp_position)
//        val tvProfile = findViewById<TextView>(R.id.tv_profile)
//        tvProfile.text = sharedPref.getString(Constants.PREF_ID)
//        val textId = ""
//        if(sharedPref.getBoolean(Constants.PREF_IS_LOGIN)){
//
//            FirebaseFirestore.getInstance().collection("users")
//                .get()
//                .addOnSuccessListener {
//                    for (document in it){
//                        if(document.data.get("email") as String == sharedPref.getString(Constants.PREF_USERNAME)){
//                            idUser = document.data.get("id") as String
//                            etNama.text = document.data.get("name") as String
//                            etEmail.text = document.data.get("email") as String
//                            spPosisi.setSelection(EditPompa().getIndex(resources.getStringArray(R.array.position), userDetails.position))
//                        }
//                    }
//                }
//        }
//    }


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