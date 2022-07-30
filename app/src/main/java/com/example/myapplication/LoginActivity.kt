package com.example.myapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class LoginActivity : BaseActivity(), View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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

    }

    override fun onClick(view: View?) {

        if (view != null){
            when(view.id) {
                R.id.tv_forgot_pass->{

                }
                R.id.btn_login->{
                    loginRegisteredUser()
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

        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
        startActivity(intent)

//        intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
        finish()
    }
}