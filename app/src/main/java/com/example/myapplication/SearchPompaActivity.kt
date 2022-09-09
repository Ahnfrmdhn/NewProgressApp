package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text

class SearchPompaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_pompa)

        val btnHasil = findViewById<Button>(R.id.btn_hasil_jenis)
        btnHasil.setOnClickListener {
            filter()
        }
    }

    private fun filter() {
        val cbSub = findViewById<CheckBox>(R.id.cb_submersible)
        val cbCent = findViewById<CheckBox>(R.id.cb_centrifugal)
        val cbSampit = findViewById<CheckBox>(R.id.cb_sampit)
        val cbJet = findViewById<CheckBox>(R.id.cb_jetpump)

        val tvHasil = findViewById<TextView>(R.id.tv_hasil)

        val tvUtara = findViewById<TextView>(R.id.tv_utara)
        val tvUtaraHidup = findViewById<TextView>(R.id.tv_utara_hidup)
        val tvUtaraMati = findViewById<TextView>(R.id.tv_utara_mati)
        val tvSelatan = findViewById<TextView>(R.id.tv_selatan)
        val tvSelatanHidup = findViewById<TextView>(R.id.tv_selatan_hidup)
        val tvSelatanMati = findViewById<TextView>(R.id.tv_selatan_mati)
        val tvTimur = findViewById<TextView>(R.id.tv_timur)
        val tvTimurHidup = findViewById<TextView>(R.id.tv_timur_hidup)
        val tvTimurMati = findViewById<TextView>(R.id.tv_timur_mati)
        val tvKdrusa = findViewById<TextView>(R.id.tv_kdrusa)
        val tvKdrusaHidup = findViewById<TextView>(R.id.tv_kdrusa_hidup)
        val tvKdrusaMati = findViewById<TextView>(R.id.tv_kdrusa_mati)
        val tvPusat = findViewById<TextView>(R.id.tv_pusat)
        val tvPusatHidup = findViewById<TextView>(R.id.tv_pusat_hidup)
        val tvPusatMati = findViewById<TextView>(R.id.tv_pusat_mati)
        val tvBarat = findViewById<TextView>(R.id.tv_barat_hidup)
        val tvBaratHidup = findViewById<TextView>(R.id.tv_barat_hidup)
        val tvBaratMati = findViewById<TextView>(R.id.tv_barat_mati)
        val tvTgmon = findViewById<TextView>(R.id.tv_tgmonas)
        val tvTgmonHidup = findViewById<TextView>(R.id.tv_tgmonas_hidup)
        val tvTgmonMati = findViewById<TextView>(R.id.tv_tgmonas_mati)
        val tvTgpro = findViewById<TextView>(R.id.tv_tgpro)
        val tvTgproHidup = findViewById<TextView>(R.id.tv_tgpro_hidup)
        val tvTgproMati = findViewById<TextView>(R.id.tv_tgpro_mati)

        var textSub = " "
        var textCent = " "
        var textSampit = " "
        var textJet = " "

        var iHUtara = 0
        var iMUtara = 0
        var iHSelatan = 0
        var iMSelatan = 0
        var iHTimur = 0
        var iMTimur = 0
        var iHKdrusa = 0
        var iMKdrusa = 0
        var iHPusat = 0
        var iMPusat = 0
        var iHBarat = 0
        var iMBarat = 0
        var iHTgmon = 0
        var iMTgmon = 0
        var iHTgpro = 0
        var iMTgpro = 0

        FirebaseFirestore.getInstance().collection(Constants.POMPA)
            .get()
            .addOnSuccessListener {
                for(document in it){
                    val bacaJenis = document.data.get("jenis") as String
                    val bacaLokasi = document.data.get("lokasi") as String
                    val bacaStatus = document.data.get("status") as String
                    if(cbSub.isChecked){
                        textSub = "{submersible}"
                        if(bacaJenis == "submersible"){
                            if(bacaLokasi == "utara"){
                                if(bacaStatus == "hidup"){
                                    iHUtara++
                                }else if(bacaStatus == "mati"){
                                    iMUtara++
                                }
                            }
                            if(bacaLokasi == "selatan"){
                                if(bacaStatus == "hidup"){
                                    iHSelatan++
                                }else if(bacaStatus == "mati"){
                                    iMSelatan++
                                }
                            }
                            if(bacaLokasi == "timur"){
                                if(bacaStatus == "hidup"){
                                    iHTimur++
                                }else if(bacaStatus == "mati"){
                                    iMTimur++
                                }
                            }
                            if(bacaLokasi == "kandang rusa"){
                                if(bacaStatus == "hidup"){
                                    iHKdrusa++
                                }else if(bacaStatus == "mati"){
                                    iMKdrusa++
                                }
                            }
                            if(bacaLokasi == "pusat"){
                                if(bacaStatus == "hidup"){
                                    iHPusat++
                                }else if(bacaStatus == "mati"){
                                    iMPusat++
                                }
                            }
                            if(bacaLokasi == "barat"){
                                if(bacaStatus == "hidup"){
                                    iHBarat++
                                }else if(bacaStatus == "mati"){
                                    iMBarat++
                                }
                            }
                            if(bacaLokasi == "tugu monas"){
                                if(bacaStatus == "hidup"){
                                    iHTgmon++
                                }else if(bacaStatus == "mati"){
                                    iMTgmon++
                                }
                            }
                            if(bacaLokasi == "tugu proklamasi"){
                                if(bacaStatus == "hidup"){
                                    iHTgpro++
                                }else if(bacaStatus == "mati"){
                                    iMTgpro++
                                }
                            }
                        }
                    }
                    if(cbCent.isChecked){
                        textCent = "{centrifugal}"
                        if(bacaJenis == "centrifugal"){
                            if(bacaLokasi == "utara"){
                                if(bacaStatus == "hidup"){
                                    iHUtara++
                                }else if(bacaStatus == "mati"){
                                    iMUtara++
                                }
                            }
                            if(bacaLokasi == "selatan"){
                                if(bacaStatus == "hidup"){
                                    iHSelatan++
                                }else if(bacaStatus == "mati"){
                                    iMSelatan++
                                }
                            }
                            if(bacaLokasi == "timur"){
                                if(bacaStatus == "hidup"){
                                    iHTimur++
                                }else if(bacaStatus == "mati"){
                                    iMTimur++
                                }
                            }
                            if(bacaLokasi == "kandang rusa"){
                                if(bacaStatus == "hidup"){
                                    iHKdrusa++
                                }else if(bacaStatus == "mati"){
                                    iMKdrusa++
                                }
                            }
                            if(bacaLokasi == "pusat"){
                                if(bacaStatus == "hidup"){
                                    iHPusat++
                                }else if(bacaStatus == "mati"){
                                    iMPusat++
                                }
                            }
                            if(bacaLokasi == "barat"){
                                if(bacaStatus == "hidup"){
                                    iHBarat++
                                }else if(bacaStatus == "mati"){
                                    iMBarat++
                                }
                            }
                            if(bacaLokasi == "tugu monas"){
                                if(bacaStatus == "hidup"){
                                    iHTgmon++
                                }else if(bacaStatus == "mati"){
                                    iMTgmon++
                                }
                            }
                            if(bacaLokasi == "tugu proklamasi"){
                                if(bacaStatus == "hidup"){
                                    iHTgpro++
                                }else if(bacaStatus == "mati"){
                                    iMTgpro++
                                }
                            }
                        }
                    }
                    if(cbSampit.isChecked){
                        textSampit = "{sampit}"
                        if(bacaJenis == "sampit"){
                            if(bacaLokasi == "utara"){
                                if(bacaStatus == "hidup"){
                                    iHUtara++
                                }else if(bacaStatus == "mati"){
                                    iMUtara++
                                }
                            }
                            if(bacaLokasi == "selatan"){
                                if(bacaStatus == "hidup"){
                                    iHSelatan++
                                }else if(bacaStatus == "mati"){
                                    iMSelatan++
                                }
                            }
                            if(bacaLokasi == "timur"){
                                if(bacaStatus == "hidup"){
                                    iHTimur++
                                }else if(bacaStatus == "mati"){
                                    iMTimur++
                                }
                            }
                            if(bacaLokasi == "kandang rusa"){
                                if(bacaStatus == "hidup"){
                                    iHKdrusa++
                                }else if(bacaStatus == "mati"){
                                    iMKdrusa++
                                }
                            }
                            if(bacaLokasi == "pusat"){
                                if(bacaStatus == "hidup"){
                                    iHPusat++
                                }else if(bacaStatus == "mati"){
                                    iMPusat++
                                }
                            }
                            if(bacaLokasi == "barat"){
                                if(bacaStatus == "hidup"){
                                    iHBarat++
                                }else if(bacaStatus == "mati"){
                                    iMBarat++
                                }
                            }
                            if(bacaLokasi == "tugu monas"){
                                if(bacaStatus == "hidup"){
                                    iHTgmon++
                                }else if(bacaStatus == "mati"){
                                    iMTgmon++
                                }
                            }
                            if(bacaLokasi == "tugu proklamasi"){
                                if(bacaStatus == "hidup"){
                                    iHTgpro++
                                }else if(bacaStatus == "mati"){
                                    iMTgpro++
                                }
                            }
                        }
                    }
                    if(cbJet.isChecked){
                        textJet = "{jetpump}"
                        if(bacaJenis == "jetpump"){
                            if(bacaLokasi == "utara"){
                                if(bacaStatus == "hidup"){
                                    iHUtara++
                                }else if(bacaStatus == "mati"){
                                    iMUtara++
                                }
                            }
                            if(bacaLokasi == "selatan"){
                                if(bacaStatus == "hidup"){
                                    iHSelatan++
                                }else if(bacaStatus == "mati"){
                                    iMSelatan++
                                }
                            }
                            if(bacaLokasi == "timur"){
                                if(bacaStatus == "hidup"){
                                    iHTimur++
                                }else if(bacaStatus == "mati"){
                                    iMTimur++
                                }
                            }
                            if(bacaLokasi == "kandang rusa"){
                                if(bacaStatus == "hidup"){
                                    iHKdrusa++
                                }else if(bacaStatus == "mati"){
                                    iMKdrusa++
                                }
                            }
                            if(bacaLokasi == "pusat"){
                                if(bacaStatus == "hidup"){
                                    iHPusat++
                                }else if(bacaStatus == "mati"){
                                    iMPusat++
                                }
                            }
                            if(bacaLokasi == "barat"){
                                if(bacaStatus == "hidup"){
                                    iHBarat++
                                }else if(bacaStatus == "mati"){
                                    iMBarat++
                                }
                            }
                            if(bacaLokasi == "tugu monas"){
                                if(bacaStatus == "hidup"){
                                    iHTgmon++
                                }else if(bacaStatus == "mati"){
                                    iMTgmon++
                                }
                            }
                            if(bacaLokasi == "tugu proklamasi"){
                                if(bacaStatus == "hidup"){
                                    iHTgpro++
                                }else if(bacaStatus == "mati"){
                                    iMTgpro++
                                }
                            }
                        }
                    }
                }

                tvHasil.text = "Hasil untuk jenis : $textSub $textCent $textSampit $textJet"

                tvUtara.text = "Jumlah pompa di Utara : ${iHUtara+iMUtara}"
                tvUtaraHidup.text = "Jumlah pompa hidup : $iHUtara"
                tvUtaraMati.text = "Jumlah pompa mati : $iMUtara"
                tvSelatan.text = "Jumlah pompa di Selatan : ${iHSelatan+iMSelatan}"
                tvSelatanHidup.text = "Jumlah pompa hidup : $iHSelatan"
                tvSelatanMati.text = "Jumlah pompa mati : $iMSelatan"
                tvTimur.text = "Jumlah pompa di Timur : ${iHTimur+iMTimur}"
                tvTimurHidup.text = "Jumlah pompa hidup : $iHTimur"
                tvTimurMati.text = "Jumlah pompa mati : $iMTimur"
                tvKdrusa.text = "Jumlah pompa di Kandang Rusa : ${iHKdrusa+iMKdrusa}"
                tvKdrusaHidup.text = "Jumlah pompa hidup : $iHKdrusa"
                tvKdrusaMati.text = "Jumlah pompa mati : $iMKdrusa"
                tvPusat.text = "Jumlah pompa di Pusat : ${iHPusat+iMPusat}"
                tvPusatHidup.text = "Jumlah pompa hidup : $iHPusat"
                tvPusatMati.text = "Jumlah pompa mati : $iMPusat"
                tvBarat.text = "Jumlah pompa di Barat : ${iHBarat+iMBarat}"
                tvBaratHidup.text = "Jumlah pompa hidup : $iHBarat"
                tvBaratMati.text = "Jumlah pompa mati : $iMBarat"
                tvTgmon.text = "Jumlah pompa di Tugu Monas : ${iHTgmon+iMTgmon}"
                tvTgmonHidup.text = "Jumlah pompa hidup : $iHTgmon"
                tvTgmonMati.text = "Jumlah pompa mati : $iMTgmon"
                tvTgpro.text = "Jumlah pompa di Tugu Proklamasi : ${iHTgpro+iMTgpro}"
                tvTgproHidup.text = "Jumlah pompa hidup : $iHTgpro"
                tvTgproMati.text = "Jumlah pompa mati : $iMTgpro"
            }
    }
}