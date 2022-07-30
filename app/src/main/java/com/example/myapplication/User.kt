package com.example.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User (
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val image: String = "",
    val position: String = "",
    val profileCompleted: Int = 0
): Parcelable
