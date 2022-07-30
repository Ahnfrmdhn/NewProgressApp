package com.example.myapplication

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions

class FirestroreClass {

    private val mFirestore:FirebaseFirestore = FirebaseFirestore.getInstance()
    private val mFirestoreAuth = FirebaseAuth.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User){
        mFirestore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Gagal ketika mendaftar",
                    e
                )
            }
    }

    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }

    fun getUserDetails(activity: Activity){
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.i(activity.javaClass.simpleName, document.toString())

                val user = document.toObject(User::class.java)!!

                val sharedPreferences =
                    activity.getSharedPreferences(
                        Constants.MYSHOPPAL_PREFERENCES,
                        Context.MODE_PRIVATE
                    )

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(
                    Constants.LOGGED_IN_USERNAME,
                    "${user.name} ${user.email}"
                )
                editor.apply()

                when (activity) {
                    is LoginActivity -> {
                        activity.userLoggedInSuccess(user)
                    }
                }
            }
    }

    fun getUser(): FirebaseUser?{
        return mFirestoreAuth.currentUser
    }

    fun createUser(): Task<AuthResult>{
        return mFirestoreAuth.signInAnonymously()
    }


    fun getPostList(): Task<DocumentSnapshot> {
        return mFirestore
            .collection("pompa")
            .document(getCurrentUserID())
            .get()
    }




//    fun updateProfile(activity: UserProfileActivity, userInfo: User){
//
//        mFirestore.collection(Constants.USERS)
//            .document(userInfo.id)
//            .update(mapOf(
//                "name" to userInfo.name,
//                "email" to userInfo.email,
//                "position" to userInfo.position
//            )).addOnSuccessListener {
//                Toast.makeText(
//                    activity,
//                    "Data disimpan",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//            .addOnFailureListener { e ->
//                activity.hideProgressDialog()
//                Log.e(
//                    activity.javaClass.simpleName,
//                    "Gagal ketika mendaftar",
//                    e
//                )
//            }
//    }

}