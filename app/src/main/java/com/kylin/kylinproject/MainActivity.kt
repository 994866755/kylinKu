package com.kylin.kylinproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.viewpager2.widget.ViewPager2

class MainActivity : ComponentActivity() {

    var tvTest: TextView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_main)
        val viewpager : ViewPager2 = findViewById(R.id.vp)

    }


}