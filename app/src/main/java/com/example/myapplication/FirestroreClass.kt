package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.myapplication.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.HashMap

class FirestroreClass {

    private val mFirestore:FirebaseFirestore = FirebaseFirestore.getInstance()
    private val mFirestoreAuth = FirebaseAuth.getInstance()
    lateinit var sharedPref: PreferenceHelper

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
        sharedPref = PreferenceHelper(activity)
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

//    fun uploadImageToCloudeStorage(activity: Activity, imageFileURI: Uri?){
//        val sRef = FirebaseStorage.getInstance().reference.child(
//            "images/" + Constants.getFileExtension(activity, imageFileURI)
//                    + Constants.IMAGE_POMPA + System.currentTimeMillis() + "." + Constants.getFileExtension(
//                activity, imageFileURI
//        ))
//        sRef.putFile(imageFileURI!!)
//            .addOnSuccessListener { taskSnapshot ->
//            Log.e(
//                "Firebase Image URL",
//                taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
//            )
//
//            taskSnapshot.metadata!!.reference!!.downloadUrl
//                .addOnSuccessListener { uri ->
//                    Log.e("Downloadable Image URL", uri.toString())
//                    when(activity){
//                        is InputPompa -> {
//                            activity.imageUploadSuccess(uri.toString())
//                        }
//                    }
//                }
//        }
//            .addOnFailureListener{ exception ->
//                //kalau force close hapus ini
//                when (activity) {
//                    is InputPompa ->{
//                        activity.hideProgressDialog()
//                    }
//                }
//                Log.e(
//                    activity.javaClass.simpleName,
//                    exception.message,
//                    exception
//                )
//            }
//
//    }

}
