package com.adityafakhri.medfluffy.presentation.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adityafakhri.medfluffy.R
import com.adityafakhri.medfluffy.presentation.adapter.Article
import com.adityafakhri.medfluffy.presentation.adapter.HomeAdapter
import com.adityafakhri.medfluffy.presentation.ui.darkmode.DarkModeActivity
import com.adityafakhri.medfluffy.presentation.ui.detail.DetailArticleActivity
import com.google.android.material.appbar.MaterialToolbar

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val list = ArrayList<Article>()

    private lateinit var toolbar: MaterialToolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        toolbar = view.findViewById(R.id.topAppBar)

        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)

        setHasOptionsMenu(true)

        recyclerView = view.findViewById(R.id.rv_article)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        list.addAll(getArticle())
        showRecyclerList()

        return view
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_appbar_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_darkmode -> {
                Intent(context, DarkModeActivity::class.java).also {
                    startActivity(it)
                }
                true
            }
            R.id.menu_lang -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showRecyclerList() {
        val listAdapter = HomeAdapter(list)
        recyclerView.adapter = listAdapter
        recyclerView.setHasFixedSize(true)

        listAdapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Article) {
                val intentToDetail = Intent(requireContext(), DetailArticleActivity::class.java)
                intentToDetail.putExtra(DetailArticleActivity.EXTRA_DETAIL, data)
                startActivity(intentToDetail)
            }
        })
    }

    @SuppressLint("Recycle")
    private fun getArticle(): ArrayList<Article> {
        val id = resources.getStringArray(R.array.data_id)

        val source = resources.getStringArray(R.array.data_source)
        val title = resources.getStringArray(R.array.data_title)
        val img = resources.obtainTypedArray(R.array.data_img)
        val description = resources.getStringArray(R.array.data_description)
        val listArticles = ArrayList<Article>()
        for (i in source.indices) {
            val article =
                Article(id[i], source[i], title[i], description[i], img.getResourceId(i, -1))
            listArticles.add(article)
        }
        return listArticles
    }

}