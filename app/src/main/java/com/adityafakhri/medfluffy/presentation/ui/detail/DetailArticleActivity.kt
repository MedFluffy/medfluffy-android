package com.adityafakhri.medfluffy.presentation.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adityafakhri.medfluffy.R

class DetailArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)
    }

    companion object {
        const val EXTRA_STADIUM = "extra_stadium"
    }
}