package com.adityafakhri.medfluffy.presentation

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.adityafakhri.medfluffy.R
import com.adityafakhri.medfluffy.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.elevation.SurfaceColors


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

//        val color = SurfaceColors.SURFACE_2.getColor(this)
//        window.statusBarColor = color
//        window.navigationBarColor = color

//        navView.menu.findItem(R.id.navigation_empty).isEnabled = false
//        navView.setOnItemSelectedListener { item ->
//            when (item.itemId
//            ) {
//                R.id.navigation_home -> {
//                    item.setIcon(R.drawable.ic_home_active)
//                }
//                R.id.navigation_history -> {
//                    item.setIcon(R.drawable.baseline_history_24)
//
//                    navView.menu.findItem(R.id.navigation_home)?.setIcon(R.drawable.ic_home_inactive)
//                }
//            }
//            true
//        }
    }
}