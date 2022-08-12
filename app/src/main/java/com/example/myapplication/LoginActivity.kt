package com.example.myapplication

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.model.User
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class LoginActivity : BaseActivity(), View.OnClickListener{
    var context = this
    var connectivity: ConnectivityManager? = null
    var info: NetworkInfo? = null
    var check: Boolean = false

    lateinit var sharedPref : PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPref = PreferenceHelper(this)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        val tvForgotPass = findViewById<TextView>(R.id.tv_forgot_pass)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val tvRegister = findViewById<TextView>(R.id.tv_register)
        tvForgotPass.setOnClickListener{ onClick(tvForgotPass)}
        btnLogin.setOnClickListener{onClick(btnLogin)}
        tvRegister.setOnClickListener{onClick(tvRegister)}

        val tvEmail = findViewById<TextView>(R.id.et_email)
        val tvPass = findViewById<TextView>(R.id.et_password)
        val cbRemember = findViewById<CheckBox>(R.id.cb_remember)
        tvEmail.text = sharedPref.getString(Constants.PREF_USERNAME)
        tvPass.text = sharedPref.getString(Constants.PREF_PASSWORD)
        cbRemember.isChecked = true

    }

    override fun onClick(view: View?) {

        if (view != null){
            when(view.id) {
                R.id.tv_forgot_pass->{

                }
                R.id.btn_login->{
                    connectivity = context.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
                    if (connectivity != null){
                        info = connectivity!!.activeNetworkInfo
                        if (info != null){
                            if (info!!.state == NetworkInfo.State.CONNECTED){

                                loginRegisteredUser()

                            }
                        }else{
                            Toast.makeText(this, "Tidak ada internet", Toast.LENGTH_SHORT).show()
                        }
                    }


                }
                R.id.tv_register->{
                    val intent = Intent(this, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun validateLoginDetails(): Boolean {
        val etMail = findViewById<TextInputEditText>(R.id.et_email)
        val etPass = findViewById<TextInputEditText>(R.id.et_password)
        return when {
            TextUtils.isEmpty(etMail.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_mail), true)
                false
            }
            TextUtils.isEmpty(etPass.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_pass), true)
                false
            }

            else -> {
//                showErrorSnackBar("Benar.", false)
                true
            }
        }
    }

    private fun loginRegisteredUser(){
        if (validateLoginDetails()) {
            showProgressDialog("Tunggu sebentar")

            val email = findViewById<TextInputEditText>(R.id.et_email).text.toString().trim{it <= ' '}
            val password = findViewById<TextInputEditText>(R.id.et_password).text.toString().trim{it <= ' '}

            val cbRemember = findViewById<CheckBox>(R.id.cb_remember)
            if(cbRemember.isChecked){
                sharedPref.put(Constants.PREF_USERNAME, email)
                sharedPref.put(Constants.PREF_PASSWORD, password)
                sharedPref.put(Constants.PREF_CB, true)
            }


            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->

                    if (task.isSuccessful){
                        FirestroreClass().getUserDetails(this@LoginActivity)

                    }else{
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                        hideProgressDialog()
                    }
                }
        }
    }

    fun userLoggedInSuccess (user: User){
        hideProgressDialog()

        Log.i("Nama :", user.name)
        Log.i("Email : ", user.email)
        Log.i("Posisi :", user.position)

        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
//        sharedPref.put(Constants.PREF_NAME, user.name)
//        sharedPref.put(Constants.PREF_ID, user.id)
//        sharedPref.put(Constants.PREF_POSISI, user.position)
//        sharedPref.put(Constants.PREF_IS_LOGIN, true)
        intent.putExtra("EXIT", true)
        startActivity(intent)
//        intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
        finish()
    }

//    override fun onStart() {
//        super.onStart()
//        if(sharedPref.getBoolean(Constants.PREF_IS_LOGIN)){
//            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//
//    }

}