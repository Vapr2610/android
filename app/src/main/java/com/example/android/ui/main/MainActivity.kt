package com.example.android.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.databinding.ActivityMainBinding
import com.example.android.ui.calculator.CalculatorActivity
import com.example.android.ui.location.LocationActivity
import com.example.android.ui.player.MediaPlayerActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnCalculator.setOnClickListener {
            startActivity(Intent(this, CalculatorActivity::class.java))
        }

        binding.btnPlayer.setOnClickListener {
            startActivity(Intent(this, MediaPlayerActivity::class.java))
        }

        binding.btnLocation.setOnClickListener {
            startActivity(Intent(this, LocationActivity::class.java))
        }
    }
}