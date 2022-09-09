package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import androidx.core.app.ActivityCompat
import com.google.firebase.firestore.FirebaseFirestore
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.util.jar.Manifest

class ExcelHelper() {

    private var db = FirebaseFirestore.getInstance().collection(Constants.POMPA)
    fun excelCreater(){

        db.get()
            .addOnSuccessListener {
                val HSSFWorkbook = HSSFWorkbook()
                val HSSFSheet = HSSFWorkbook.createSheet()
                var i: Int = 0
                /*for (document in it){
                    HSSFSheet.createRow(i).createCell(0).setCellValue(document.data.get("lokasi") as String)
                    HSSFSheet.createRow(i).createCell(1).setCellValue(document.data.get("jenis") as String)
                    HSSFSheet.createRow(i).createCell(2).setCellValue(document.data.get("kapasitas") as String)
                    HSSFSheet.createRow(i).createCell(3).setCellValue(document.data.get("satuan") as String)
                    HSSFSheet.createRow(i).createCell(4).setCellValue(document.data.get("status") as String)
                    HSSFSheet.createRow(i).createCell(5).setCellValue(document.data.get("keterangan") as String)
                    i++
                }*/
                HSSFSheet.createRow(i).createCell(0).setCellValue("nyoba")
                val file =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val output = FileOutputStream("$file/filepompa.xlsx")
                HSSFWorkbook.write(output)
                output.close()
            }
    }


}