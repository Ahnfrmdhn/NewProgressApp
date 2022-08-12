package com.example.myapplication.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import java.io.IOException

class GlideLoader(val context: Context) {
    fun loaderPicture(imageURI: Uri, imageView: ImageView){
        try {
            Glide
                .with(context)
                .load(imageURI)
                .centerCrop()
                .placeholder(R.drawable.img)
                .into(imageView)
        } catch (e: IOException){
            e.printStackTrace()
        }
    }
}