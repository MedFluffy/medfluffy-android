package com.adityafakhri.medfluffy.presentation.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.adityafakhri.medfluffy.R
import com.adityafakhri.medfluffy.databinding.ActivityDetailArticleBinding
import com.adityafakhri.medfluffy.presentation.adapter.Article
import com.google.android.material.snackbar.Snackbar

class DetailArticleActivity : AppCompatActivity() {

    private var _binding: ActivityDetailArticleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite -> {
                    val contextView = binding.root
                    Snackbar.make(contextView, R.string.bookmark, Snackbar.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        val data = if (Build.VERSION.SDK_INT >= 33) intent.getParcelableExtra("key_article", Article::class.java) as Article
        else @Suppress("DEPRECATION")
        intent.getParcelableExtra("key_article")

        if (data != null) {
            binding.apply {
                tvSource.text = data.source
                tvTitle.text = data.title
                ivArticle.setImageResource(data.img)
                tvDescription.text = data.description
            }
        }
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }
}