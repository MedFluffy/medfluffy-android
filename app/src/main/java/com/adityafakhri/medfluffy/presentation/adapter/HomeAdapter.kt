package com.adityafakhri.medfluffy.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adityafakhri.medfluffy.R
import com.adityafakhri.medfluffy.presentation.ui.detail.DetailArticleActivity

class HomeAdapter(
    private val listArticle: ArrayList<Article>
) : RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun getItemCount(): Int = listArticle.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (source, title, description, img) = listArticle[position]
        holder.tvSource.text = source
        holder.tvTitle.text = title
        holder.tvDescription.text = description
        holder.ivArticle.setImageResource(img)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailArticleActivity::class.java)
            intentDetail.putExtra("key_article", listArticle[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Article)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSource: TextView = itemView.findViewById(R.id.tv_source)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        val ivArticle: ImageView = itemView.findViewById(R.id.img_article)
    }
}