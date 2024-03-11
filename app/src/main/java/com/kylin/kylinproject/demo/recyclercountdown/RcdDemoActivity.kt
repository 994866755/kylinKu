package com.kylin.kylinproject.demo.recyclercountdown

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kylin.baselibrary.recycler.RecyclerCountDownManager
import com.kylin.kylinproject.R

class RcdDemoActivity : ComponentActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_rcd)

        val recycler : RecyclerView = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val list : ArrayList<RcdItemData> = ArrayList()
        list.add(RcdItemData(0, 100))
        list.add(RcdItemData(1, 200))
        list.add(RcdItemData(2, 300))
        list.add(RcdItemData(3, 400))
        list.add(RcdItemData(4, 500))
        list.add(RcdItemData(5, 600))
        list.add(RcdItemData(6, 600))
        list.add(RcdItemData(7, 700))
        list.add(RcdItemData(8, 800))
        list.add(RcdItemData(9, 1000))
        list.add(RcdItemData(10, 1100))
        list.add(RcdItemData(11, 1200))
        list.add(RcdItemData(12, 1300))
        list.add(RcdItemData(13, 1400))
        list.add(RcdItemData(14, 1500))
        list.add(RcdItemData(15, 1600))
        val adapter = RcdAdapter(this, list)

        recycler.adapter = adapter
        RecyclerCountDownManager.startHeartBeat()
    }

    override fun onDestroy() {
        super.onDestroy()
        RecyclerCountDownManager.clearCountDown()
        RecyclerCountDownManager.releaseHolderObservable()
        RecyclerCountDownManager.closeHeartBeat()
    }


}