package com.dsa.practicekotlin2.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dsa.practicekotlin2.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HoroscopeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horoscope_detail)
    }
}