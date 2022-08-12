package com.example.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//class Pompa (
//    val id: String?,
//    val lokasi: String,
//    val jenis: String,
//    val kapasitas: String,
//    val satuan: String,
//    val status: String
//) {
//    constructor() : this("","","","","",""){
//
//    }
//}
@Parcelize
data class Pompa (
    val id: String,
    val lokasi: String,
    val jenis: String,
    val kapasitas: String,
    val satuan: String,
    val status: String,
    val keterangan: String,
    val image: String
) : Parcelable