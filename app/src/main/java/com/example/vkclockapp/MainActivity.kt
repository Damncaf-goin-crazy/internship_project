package com.example.vkclockapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vkclockapp.drawings.ClockView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var clockView = findViewById<ClockView>(R.id.clock_view)

    }
}