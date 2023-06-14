package com.adityafakhri.medfluffy.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.adityafakhri.medfluffy.R
import com.adityafakhri.medfluffy.databinding.ActivityMainBinding
import com.adityafakhri.medfluffy.presentation.ui.upload.UploadActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fabUpload: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        window?.apply {
//            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            statusBarColor = Color.parseColor("#4552cb")
//        }

        val navView: BottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        fabUpload = binding.fab
        fabUpload.setOnClickListener {
            navigateToUploadPage()
        }

//        val color = SurfaceColors.SURFACE_2.getColor(this)
//        window.statusBarColor = color
//        window.navigationBarColor = color

        navView.menu.findItem(R.id.navigation_empty).isEnabled = false
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

    private fun navigateToUploadPage() {
        val intent = Intent(this, UploadActivity::class.java)
        startActivity(intent)
    }
}