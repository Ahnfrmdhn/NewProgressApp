package com.example.myapplication

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
data class Pompa (
    val id: String,
    val lokasi: String,
    val jenis: String,
    val kapasitas: String,
    val satuan: String,
    val status: String,
    val keterangan: String
){

}