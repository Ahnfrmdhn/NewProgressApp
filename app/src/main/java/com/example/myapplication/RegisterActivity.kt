package com.example.myapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.*
import com.example.myapplication.model.User
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : BaseActivity() {

    private lateinit var tvResult: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

//        val tvR = findViewById<TextView>(R.id.tv_result)
        val arrayPosition = resources.getStringArray(R.array.position)
        val spinner = findViewById<Spinner>(R.id.sp_position)
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                val text = parent?.getItemAtPosition(position).toString()
                tvResult = text
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        /*spinner position*/
//        val view = LayoutInflater.from(this@RegisterActivity).inflate(R.layout.activity_register, null)
//        tvResult = view.findViewById(R.id.tv_result) as TextView
//
//        val options = arrayOf("petugasme1", "petugasme2", "stafpds1", "stafpds2", "kasipds")
//
//        spPosition.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,options)
//
//        spPosition.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                tvResult!!.text = options.get(p2)
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                tvResult!!.text = "Posisi"
//            }
//        }


        val tvBacktoLogin = findViewById<TextView>(R.id.tv_back_to_login)
        tvBacktoLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val btnRegister = findViewById<Button>(R.id.btn_register)
        btnRegister.setOnClickListener {

            registerUser()
        }

    }


    private fun validateRegisterDetails(): Boolean{
        val etName = findViewById<TextInputEditText>(R.id.et_name)
        val etMail = findViewById<TextInputEditText>(R.id.et_email)
        val etPass = findViewById<TextInputEditText>(R.id.et_password)
        return when {
            TextUtils.isEmpty(etName.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_name), true)
                false
            }
            TextUtils.isEmpty(etMail.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_mail), true)
                false
            }
            TextUtils.isEmpty(etPass.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_pass), true)
                false
            }

            else -> {
                //showErrorSnackBar("Benar.", false)
                true
            }
        }
    }


    private fun registerUser(){
        val etName = findViewById<TextInputEditText>(R.id.et_name)
        val etMail = findViewById<TextInputEditText>(R.id.et_email)
        val etPass = findViewById<TextInputEditText>(R.id.et_password)
        if(validateRegisterDetails()) {

            showProgressDialog("Tunggu sebentar")
            val email: String = etMail.text.toString().trim {it <= ' '}
            val password: String = etPass.text.toString().trim {it <= ' '}

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->

//                    hideProgressDialog()

                    if(task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        val user = User(
                            firebaseUser.uid,
                            etName.text.toString().trim{it <= ' '},
                            etMail.text.toString().trim { it <= ' ' },
                            "",
                            tvResult.trim{ it <= ' '},
                        )

                        FirestroreClass().registerUser(this@RegisterActivity, user)

//                        FirebaseAuth.getInstance().signOut()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))

                    } else {
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }

    fun userRegistrationSuccess(){
        hideProgressDialog()

        Toast.makeText(
            this@RegisterActivity,
            "Daftar Berhasil",
            Toast.LENGTH_SHORT
        ).show()
    }

}