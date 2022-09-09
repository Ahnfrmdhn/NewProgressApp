package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text

class SearchActivity : AppCompatActivity() {
    var pompaMati: Int = 0
    var pompaHidup: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        val btnHasil = findViewById<Button>(R.id.btn_hasil_lokasi)
        btnHasil.setOnClickListener {
            filter()
        }


    }

//    fun checkLokasi(){
//
//        val cbSub = findViewById<CheckBox>(R.id.cb_submersible)
//        val cbCent = findViewById<CheckBox>(R.id.cb_centrifugal)
//        val cbSampit = findViewById<CheckBox>(R.id.cb_sampit)
//        val cbJet = findViewById<CheckBox>(R.id.cb_jetpump)
//
//        var i = 0
//        var j = 0
//        db.get()
//            .addOnSuccessListener {
//                for (document in it){
//                    val textLokasi = document.data.get("lokasi") as String
//                    val textJenis = document.data.get("jenis") as String
//                    val textStatus = document.data.get("status") as String
//                    if(cbUtara.isChecked){
//                        if (textLokasi == "utara") {
//                            if (textStatus == "hidup"){
//                                i += 1
//                            }else if(textStatus == "mati"){
//                                j += 1
//                            }
//                        }
//                    }
//                    if(cbSelatan.isChecked){
//                        if (textLokasi == "selatan"){
//                            if(textStatus == "hidup"){
//                                i += 1
//                            }else if (textStatus == "mati"){
//                                j += 1
//                            }
//                        }
//                    }
//                    if(cbTimur.isChecked){
//                        if(textLokasi == "timur"){
//                            if(textStatus == "hidup"){
//                                i += 1
//                            }else if (textStatus == "mati"){
//                                j += 1
//                            }
//                        }
//                    }
//                    if(cbKdRusa.isChecked){
//                        if(textLokasi == "kandang rusa"){
//                            if(textStatus == "hidup"){
//                                i += 1
//                            }else if (textStatus == "mati"){
//                                j += 1
//                            }
//                        }
//                    }
//                    if(cbPusat.isChecked){
//                        if (textLokasi == "pusat"){
//                            if(textStatus == "hidup"){
//                                i += 1
//                            }else if (textStatus == "mati"){
//                                j += 1
//                            }
//                        }
//                    }
//                    if(cbBarat.isChecked){
//                        if(textLokasi == "barat"){
//                            if(textStatus == "hidup"){
//                                i += 1
//                            }else if (textStatus == "mati"){
//                                j += 1
//                            }
//                        }
//                    }
//                    if(cbTgMonas.isChecked){
//                        if(textLokasi == "tugu monas"){
//                            if(textStatus == "hidup"){
//                                i += 1
//                            }else if (textStatus == "mati"){
//                                j += 1
//                            }
//                        }
//                    }
//                    if(cbTgPro.isChecked){
//                        if(textLokasi ==  "tugu proklamasi"){
//                            if(textStatus == "hidup"){
//                                i += 1
//                            }else if (textStatus == "mati"){
//                                j += 1
//                            }
//                        }
//                    }
//
//
//                    if(cbSub.isChecked){
//                        if(textJenis == "submersible"){
//                            if(textStatus == "hidup"){
//                                i += 1
//                            }else if (textStatus == "mati"){
//                                j += 1
//                            }
//                        }
//                    }
//                    if(cbCent.isChecked){
//                        if(textJenis == "centrifugal"){
//                            if(textStatus == "hidup"){
//                                i += 1
//                            }else if (textStatus == "mati"){
//                                j += 1
//                            }
//                        }
//                    }
//                    if(cbSampit.isChecked){
//                        if(textJenis == "sampit"){
//                            if(textStatus == "hidup"){
//                                i += 1
//                            }else if (textStatus == "mati"){
//                                j += 1
//                            }
//                        }
//                    }
//                    if (cbJet.isChecked){
//                        if(textJenis == "jetpump"){
//                            if(textStatus == "hidup"){
//                                i += 1
//                            }else if (textStatus == "mati"){
//                                j += 1
//                            }
//                        }
//                    }
//
//                }
//                val tvPompaHidup = findViewById<TextView>(R.id.tv_pompahidup)
//                val tvPompaMati = findViewById<TextView>(R.id.tv_pompamati)
//                tvPompaHidup.text = "Jumlah pompa hidup : ${i.toString()}"
//                tvPompaMati.text = "Jumlah pompa mati : ${j.toString()}"
//            }
//
//    }



    private fun filter(){
        var db = FirebaseFirestore.getInstance()
        val cbUtara = findViewById<CheckBox>(R.id.cb_utara)
        val cbSelatan = findViewById<CheckBox>(R.id.cb_selatan)
        val cbTimur = findViewById<CheckBox>(R.id.cb_timur)
        val cbKdRusa = findViewById<CheckBox>(R.id.cb_kandangrusa)
        val cbPusat = findViewById<CheckBox>(R.id.cb_pusat)
        val cbBarat = findViewById<CheckBox>(R.id.cb_barat)
        val cbTgMonas = findViewById<CheckBox>(R.id.cb_tugumonas)
        val cbTgPro = findViewById<CheckBox>(R.id.cb_tuguproklamasi)

        val tvHasil = findViewById<TextView>(R.id.tv_hasil)
        val tvSub = findViewById<TextView>(R.id.tv_sub)
        val tvSubHidup = findViewById<TextView>(R.id.tv_sub_hidup)
        val tvSubMati = findViewById<TextView>(R.id.tv_sub_mati)
        val tvCent = findViewById<TextView>(R.id.tv_cent)
        val tvCentHidup = findViewById<TextView>(R.id.tv_cent_hidup)
        val tvCentMati = findViewById<TextView>(R.id.tv_cent_mati)
        val tvSampit = findViewById<TextView>(R.id.tv_sampit)
        val tvSampitHidup = findViewById<TextView>(R.id.tv_sampit_hidup)
        val tvSampitMati = findViewById<TextView>(R.id.tv_sampit_mati)
        val tvJet = findViewById<TextView>(R.id.tv_jet)
        val tvJetHidup = findViewById<TextView>(R.id.tv_jet_hidup)
        val tvJetMati = findViewById<TextView>(R.id.tv_jet_mati)

        var textUtara = " "
        var textSelatan = " "
        var textTimur = " "
        var textKdRusa = " "
        var textPusat = " "
        var textBarat = " "
        var textTgMonas = " "
        var textTgPro = " "


        var iHidupSub =  0
        var iMatiSub = 0
        var iHidupCent = 0
        var iMatiCent = 0
        var iHidupSampit = 0
        var iMatiSampit = 0
        var iHidupJet = 0
        var iMatiJet = 0


        FirebaseFirestore.getInstance().collection(Constants.POMPA).get()
            .addOnSuccessListener {
                for (document in it){
                    val bacaJenis = document.data.get("jenis") as String
                    val bacaLokasi = document.data.get("lokasi") as String
                    val bacaStatus = document.data.get("status") as String
                    if(cbUtara.isChecked){
                        textUtara = "{utara}"
                        if(bacaLokasi == "utara"){
                            if(bacaJenis == "submersible"){
                                if (bacaStatus == "hidup"){
                                    iHidupSub++
                                }else if (bacaStatus == "mati"){
                                    iMatiSub++
                                }
                            }
                            if(bacaJenis == "centrifugal"){
                                if (bacaStatus == "hidup"){
                                    iHidupCent++
                                }else if (bacaStatus == "mati"){
                                    iMatiCent++
                                }
                            }
                            if(bacaJenis == "sampit"){
                                if (bacaStatus == "hidup"){
                                    iHidupSampit++
                                }else if (bacaStatus == "mati"){
                                    iMatiSampit++
                                }
                            }
                            if(bacaJenis == "jetpump"){
                                if (bacaStatus == "hidup"){
                                    iHidupJet++
                                }else if (bacaStatus == "mati"){
                                    iMatiJet++
                                }
                            }
                        }
                    }
                    if(cbSelatan.isChecked){
                        textSelatan = "{selatan}"
                        if(bacaLokasi == "selatan"){
                            if(bacaJenis == "submersible"){
                                if (bacaStatus == "hidup"){
                                    iHidupSub++
                                }else if (bacaStatus == "mati"){
                                    iMatiSub++
                                }
                            }
                            if(bacaJenis == "centrifugal"){
                                if (bacaStatus == "hidup"){
                                    iHidupCent++
                                }else if (bacaStatus == "mati"){
                                    iMatiCent++
                                }
                            }
                            if(bacaJenis == "sampit"){
                                if (bacaStatus == "hidup"){
                                    iHidupSampit++
                                }else if (bacaStatus == "mati"){
                                    iMatiSampit++
                                }
                            }
                            if(bacaJenis == "jetpump"){
                                if (bacaStatus == "hidup"){
                                    iHidupJet++
                                }else if (bacaStatus == "mati"){
                                    iMatiJet++
                                }
                            }
                        }
                    }
                    if(cbTimur.isChecked){
                        textTimur = "{timur}"
                        if(bacaLokasi == "timur"){
                            if(bacaJenis == "submersible"){
                                if (bacaStatus == "hidup"){
                                    iHidupSub++
                                }else if (bacaStatus == "mati"){
                                    iMatiSub++
                                }
                            }
                            if(bacaJenis == "centrifugal"){
                                if (bacaStatus == "hidup"){
                                    iHidupCent++
                                }else if (bacaStatus == "mati"){
                                    iMatiCent++
                                }
                            }
                            if(bacaJenis == "sampit"){
                                if (bacaStatus == "hidup"){
                                    iHidupSampit++
                                }else if (bacaStatus == "mati"){
                                    iMatiSampit++
                                }
                            }
                            if(bacaJenis == "jetpump"){
                                if (bacaStatus == "hidup"){
                                    iHidupJet++
                                }else if (bacaStatus == "mati"){
                                    iMatiJet++
                                }
                            }
                        }
                    }
                    if(cbKdRusa.isChecked){
                        textKdRusa = "{kandang rusa}"
                        if(bacaLokasi == "kandang rusa"){
                            if(bacaJenis == "submersible"){
                                if (bacaStatus == "hidup"){
                                    iHidupSub++
                                }else if (bacaStatus == "mati"){
                                    iMatiSub++
                                }
                            }
                            if(bacaJenis == "centrifugal"){
                                if (bacaStatus == "hidup"){
                                    iHidupCent++
                                }else if (bacaStatus == "mati"){
                                    iMatiCent++
                                }
                            }
                            if(bacaJenis == "sampit"){
                                if (bacaStatus == "hidup"){
                                    iHidupSampit++
                                }else if (bacaStatus == "mati"){
                                    iMatiSampit++
                                }
                            }
                            if(bacaJenis == "jetpump"){
                                if (bacaStatus == "hidup"){
                                    iHidupJet++
                                }else if (bacaStatus == "mati"){
                                    iMatiJet++
                                }
                            }
                        }
                    }
                    if(cbBarat.isChecked){
                        textBarat = "{barat}"
                        if(bacaLokasi == "barat"){
                            if(bacaJenis == "submersible"){
                                if (bacaStatus == "hidup"){
                                    iHidupSub++
                                }else if (bacaStatus == "mati"){
                                    iMatiSub++
                                }
                            }
                            if(bacaJenis == "centrifugal"){
                                if (bacaStatus == "hidup"){
                                    iHidupCent++
                                }else if (bacaStatus == "mati"){
                                    iMatiCent++
                                }
                            }
                            if(bacaJenis == "sampit"){
                                if (bacaStatus == "hidup"){
                                    iHidupSampit++
                                }else if (bacaStatus == "mati"){
                                    iMatiSampit++
                                }
                            }
                            if(bacaJenis == "jetpump"){
                                if (bacaStatus == "hidup"){
                                    iHidupJet++
                                }else if (bacaStatus == "mati"){
                                    iMatiJet++
                                }
                            }
                        }
                    }
                    if(cbPusat.isChecked){
                        textPusat = "pusat"
                        if(bacaLokasi == "pusat"){
                            if(bacaJenis == "submersible"){
                                if (bacaStatus == "hidup"){
                                    iHidupSub++
                                }else if (bacaStatus == "mati"){
                                    iMatiSub++
                                }
                            }
                            if(bacaJenis == "centrifugal"){
                                if (bacaStatus == "hidup"){
                                    iHidupCent++
                                }else if (bacaStatus == "mati"){
                                    iMatiCent++
                                }
                            }
                            if(bacaJenis == "sampit"){
                                if (bacaStatus == "hidup"){
                                    iHidupSampit++
                                }else if (bacaStatus == "mati"){
                                    iMatiSampit++
                                }
                            }
                            if(bacaJenis == "jetpump"){
                                if (bacaStatus == "hidup"){
                                    iHidupJet++
                                }else if (bacaStatus == "mati"){
                                    iMatiJet++
                                }
                            }
                        }
                    }
                    if(cbTgMonas.isChecked){
                        textTgMonas = "{tugu monas}"
                        if(bacaLokasi == "tugu monas"){
                            if(bacaJenis == "submersible"){
                                if (bacaStatus == "hidup"){
                                    iHidupSub++
                                }else if (bacaStatus == "mati"){
                                    iMatiSub++
                                }
                            }
                            if(bacaJenis == "centrifugal"){
                                if (bacaStatus == "hidup"){
                                    iHidupCent++
                                }else if (bacaStatus == "mati"){
                                    iMatiCent++
                                }
                            }
                            if(bacaJenis == "sampit"){
                                if (bacaStatus == "hidup"){
                                    iHidupSampit++
                                }else if (bacaStatus == "mati"){
                                    iMatiSampit++
                                }
                            }
                            if(bacaJenis == "jetpump"){
                                if (bacaStatus == "hidup"){
                                    iHidupJet++
                                }else if (bacaStatus == "mati"){
                                    iMatiJet++
                                }
                            }
                        }
                    }
                    if(cbTgPro.isChecked){
                        textTgPro = "{tugu proklamasi}"
                        if(bacaLokasi == "tugu proklamasi"){
                            if(bacaJenis == "submersible"){
                                if (bacaStatus == "hidup"){
                                    iHidupSub++
                                }else if (bacaStatus == "mati"){
                                    iMatiSub++
                                }
                            }
                            if(bacaJenis == "centrifugal"){
                                if (bacaStatus == "hidup"){
                                    iHidupCent++
                                }else if (bacaStatus == "mati"){
                                    iMatiCent++
                                }
                            }
                            if(bacaJenis == "sampit"){
                                if (bacaStatus == "hidup"){
                                    iHidupSampit++
                                }else if (bacaStatus == "mati"){
                                    iMatiSampit++
                                }
                            }
                            if(bacaJenis == "jetpump"){
                                if (bacaStatus == "hidup"){
                                    iHidupJet++
                                }else if (bacaStatus == "mati"){
                                    iMatiJet++
                                }
                            }
                        }
                    }
                }


                tvHasil.text = "Hasil untuk lokasi: $textUtara $textSelatan $textTimur $textKdRusa $textPusat $textBarat $textTgMonas $textTgPro"
                tvSub.text = "Jumlah jenis pompa submersible : ${(iHidupSub+iMatiSub)}"
                tvSubHidup.text = "Jumlah pompa hidup : ${iHidupSub}"
                tvSubMati.text = "Jumlah pompa mati : ${iMatiSub}"
                tvCent.text = "Jumlah jenis pompa centrifugal : ${(iHidupCent+iMatiCent)}"
                tvCentHidup.text = "Jumlah pompa hidup : ${iHidupCent}"
                tvCentMati.text = "Jumlah pompa mati : ${iMatiCent}"
                tvSampit.text = "Jumlah jenis pompa sampit : ${iHidupSampit+iMatiSampit}"
                tvSampitHidup.text = "Jumlah pompa hidup : ${iHidupSampit}"
                tvSampitMati.text = "Jumlah pompa mati : ${iMatiSampit}"
                tvJet.text = "Jumlah jenis pompa jetpump : ${iHidupJet+iMatiJet}"
                tvJetHidup.text = "Jumlah pompa hidup : ${iHidupJet}"
                tvJetMati.text = "Jumlah pompa mati : ${iMatiJet}"
            }

    }
}