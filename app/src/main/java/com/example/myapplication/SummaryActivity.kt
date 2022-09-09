package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.Pompa
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text
import kotlin.math.roundToInt

class SummaryActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        //piechart untuk lokasi
        showProgressDialog("Tunggu sebentar")
        readDataLokasi("utara", R.id.pc_lokasi_utara,R.id.tv_hidup_utara, R.id.tv_mati_utara)
        readDataLokasi("selatan", R.id.pc_lokasi_selatan, R.id.tv_hidup_selatan, R.id.tv_mati_selatan)
        readDataLokasi("timur", R.id.pc_lokasi_timur, R.id.tv_hidup_timur, R.id.tv_mati_timur)
        readDataLokasi("kandang rusa", R.id.pc_lokasi_kandangrusa, R.id.tv_hidup_kandangrusa, R.id.tv_mati_kandangrusa)
        readDataLokasi("pusat", R.id.pc_lokasi_pusat, R.id.tv_hidup_pusat, R.id.tv_mati_pusat)
        readDataLokasi("barat", R.id.pc_lokasi_barat, R.id.tv_hidup_barat, R.id.tv_mati_barat)
        readDataLokasi("tugu monas", R.id.pc_lokasi_tugumonas, R.id.tv_hidup_tugumonas, R.id.tv_mati_tugumonas)
        readDataLokasi("tugu prokalamasi", R.id.pc_lokasi_tuguproklamasi, R.id.tv_hidup_tuguproklamasi, R.id.tv_mati_tuguproklamasi)

        //piechart untuk jenis pompa
        readDataJenis("submersible", R.id.pc_jenis_submersible, R.id.tv_hidup_submersible, R.id.tv_mati_submersible)
        readDataJenis("centrifugal", R.id.pc_jenis_centrifugal, R.id.tv_hidup_centrifugal, R.id.tv_mati_centrifugal)
        readDataJenis("jetpump", R.id.pc_jenis_jetpump, R.id.tv_hidup_jetpump, R.id.tv_mati_jetpump)
        readDataJenis("sampit", R.id.pc_jenis_sampit, R.id.tv_hidup_sampit, R.id.tv_mati_sampit)


    }

    fun readDataLokasi(lokasi: String, idPieChart: Int, idHidup: Int, idMati: Int){
        val db = FirebaseFirestore.getInstance()
        var iHidup = 0f
        var iMati = 0f
        db.collection(Constants.POMPA)
            .get()
            .addOnSuccessListener {
                for (document in it){
                    if(document.data.get("lokasi") as String == lokasi){
                        if(document.data.get("status") as String == "hidup"){
                            iHidup = iHidup + 1.0f
                        }else if(document.data.get("status") as String == "mati"){
                            iMati = iMati + 1.0f
                        }
                    }
                }
                val tvHasilHidup = findViewById<TextView>(idHidup)
                tvHasilHidup.text = "Jumlah pompa hidup : ${iHidup.roundToInt()}"

                val tvHasilMati = findViewById<TextView>(idMati)
                tvHasilMati.text = "Jumlah pompa mati : ${iMati.roundToInt()}"

                val pieChart = findViewById<PieChart>(idPieChart)
                viewChart(pieChart, iHidup, iMati, lokasi)
            }
            .addOnCompleteListener {
                if (it.isSuccessful){
                    hideProgressDialog()
                }
            }
    }

    fun readDataJenis(jenis: String, idPieChart: Int, idHidup: Int, idMati: Int){
        val db = FirebaseFirestore.getInstance()
        var iHidup = 0f
        var iMati = 0f
        db.collection(Constants.POMPA)
            .get()
            .addOnSuccessListener {
                for (document in it){
                    if(document.data.get("jenis") as String == jenis){
                        if(document.data.get("status") as String == "hidup"){
                            iHidup = iHidup + 1.0f
                        }else if(document.data.get("status") as String == "mati"){
                            iMati = iMati + 1.0f
                        }
                    }
                }
                val tvHasilHidup = findViewById<TextView>(idHidup)
                tvHasilHidup.text = "Jumlah pompa hidup : ${iHidup.roundToInt()}"

                val tvHasilMati = findViewById<TextView>(idMati)
                tvHasilMati.text = "Jumlah pompa mati : ${iMati.roundToInt()}"

                val pieChart = findViewById<PieChart>(idPieChart)
                viewChart(pieChart, iHidup, iMati, jenis)
            }
            .addOnCompleteListener {
                if (it.isSuccessful){
                    hideProgressDialog()
                }
            }
    }

    fun viewChart(pieChart: PieChart, hidup: Float, mati: Float, centerText: String){

        val myCustomFont : Typeface? = ResourcesCompat.getFont(this, R.font.poppinsblack)
        if (hidup == 0f && mati == 0f){
            pieChart.centerText = "Data ${centerText} belum ada"
        }else{
            pieChart.centerText = centerText
        }
        pieChart.setCenterTextTypeface(myCustomFont)

        // on below line we are setting user percent value,
        // setting description as enabled and offset for pie chart
        pieChart.setUsePercentValues(true)
        pieChart.getDescription().setEnabled(false)
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)

        // on below line we are setting drag for our pie chart
        pieChart.setDragDecelerationFrictionCoef(0.95f)

        // on below line we are setting hole
        // and hole color for pie chart
        pieChart.setDrawHoleEnabled(true)
        pieChart.setHoleColor(Color.WHITE)

        // on below line we are setting circle color and alpha
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)

        // on  below line we are setting hole readius
        pieChart.setHoleRadius(58f)
        pieChart.setTransparentCircleRadius(61f)

        // on below line we are setting center text
        pieChart.setDrawCenterText(true)

        // on below line we are setting
        // rotation for our pie chart
        pieChart.setRotationAngle(0f)

        // enable rotation of the pieChart by touch
        pieChart.setRotationEnabled(true)
        pieChart.setHighlightPerTapEnabled(true)

        // on below line we are setting animation for our pie chart
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        // on below line we are disabling our legend for pie chart
        pieChart.legend.isEnabled = false
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(12f)

        // on below line we are creating array list and
        // adding data to it to display in pie chart

        //MASUKIN DATA MATI DAN HIDUP

        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(mati, "mati"))
        entries.add(PieEntry(hidup, "hidup"))


        // on below line we are setting pie data set
        val dataSet = PieDataSet(entries, "Mobile OS")

        // on below line we are setting icons.
        dataSet.setDrawIcons(false)

        // on below line we are setting slice for pie
        dataSet.sliceSpace = 2f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors to list
        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.red))
        colors.add(resources.getColor(R.color.green))


        // on below line we are setting colors.
        dataSet.colors = colors

        // on below line we are setting pie data set
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(10f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.WHITE)
        pieChart.setData(data)

        // undo all highlights
        pieChart.highlightValues(null)

        // loading chart
        pieChart.invalidate()
    }
}