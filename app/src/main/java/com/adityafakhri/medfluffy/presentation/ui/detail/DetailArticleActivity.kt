package com.adityafakhri.medfluffy.presentation.ui.detail

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adityafakhri.medfluffy.databinding.ActivityDetailArticleBinding
import com.adityafakhri.medfluffy.presentation.adapter.Article

class DetailArticleActivity : AppCompatActivity() {

    private var _binding: ActivityDetailArticleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val data = if (Build.VERSION.SDK_INT >= 33) intent.getParcelableExtra(
            "key_article",
            Article::class.java
        ) as Article
        else @Suppress("DEPRECATION")
        intent.getParcelableExtra("key_article")

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

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