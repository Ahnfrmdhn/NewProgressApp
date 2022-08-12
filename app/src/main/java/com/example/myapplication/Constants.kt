package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

class Constants {
    companion object{
        const val USERS: String = "users"
        const val MYSHOPPAL_PREFERENCES: String = "MyShopPalPrefs"
        const val LOGGED_IN_USERNAME: String = "logged_in_username"
        const val EXTRA_USER_DETAILS: String = "extra_user_details"
        const val POMPA: String = "pompa"
        const val READ_STORAGE_PERMISSION_CODE = 2
        const val PICK_IMAGE_REQUEST_CODE = 1
        const val IMAGE_POMPA: String = "Image_Pompa"

        val PREF_IS_LOGIN = "PREF_IS_LOGIN"
        val PREF_ID = "PREF_ID"
        val PREF_POSISI = "PREF_POSISI"
        val PREF_NAME = "PREF_NAME"
        val PREF_USERNAME = "PREF_USERNAME"
        val PREF_PASSWORD = "PREF_PASS"
        val PREF_CB = "PREF_CHECK_BOX"

        fun showImageChooser(activity: Activity){

            val galeryIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )

            activity.startActivityForResult(galeryIntent, PICK_IMAGE_REQUEST_CODE)
        }

        fun getFileExtension(activity: Activity, uri: Uri?): String? {
            return MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
        }

    }




}