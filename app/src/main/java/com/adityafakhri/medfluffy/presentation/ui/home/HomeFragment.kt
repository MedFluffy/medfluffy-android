package com.adityafakhri.medfluffy.presentation.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adityafakhri.medfluffy.R
import com.adityafakhri.medfluffy.presentation.adapter.Article
import com.adityafakhri.medfluffy.presentation.adapter.HomeAdapter
import com.adityafakhri.medfluffy.presentation.ui.detail.DetailArticleActivity

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val list = ArrayList<Article>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.rv_article)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        list.addAll(getArticle())
        showRecyclerList()

        return view
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
        val source = resources.getStringArray(R.array.data_source)
        val title = resources.getStringArray(R.array.data_title)
        val img = resources.obtainTypedArray(R.array.data_img)
        val description = resources.getStringArray(R.array.data_description)
        val listStadium = ArrayList<Article>()
        for (i in source.indices) {
            val stadium = Article(source[i], title[i], description[i], img.getResourceId(i, -1))
            listStadium.add(stadium)
        }
        return listStadium
    }

}