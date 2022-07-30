package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView

class ListViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        val listView = findViewById<ListView>(R.id.lv_main)

        listView.adapter = CustomAdapter(this)
    }

    private class CustomAdapter(context: Context): BaseAdapter(){
        override fun getCount(): Int {
            TODO("Not yet implemented")
        }

        override fun getItem(p0: Int): Any {
            TODO("Not yet implemented")
        }

        override fun getItemId(p0: Int): Long {
            TODO("Not yet implemented")
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            TODO("Not yet implemented")
        }

    }
}