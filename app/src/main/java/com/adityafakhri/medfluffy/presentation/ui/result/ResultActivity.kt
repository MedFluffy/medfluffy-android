package com.adityafakhri.medfluffy.presentation.ui.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adityafakhri.medfluffy.R
import com.adityafakhri.medfluffy.databinding.ActivityResultBinding
import com.adityafakhri.medfluffy.databinding.ActivityUploadBinding

class ResultActivity : AppCompatActivity() {
    private var _binding: ActivityResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }
    }
}