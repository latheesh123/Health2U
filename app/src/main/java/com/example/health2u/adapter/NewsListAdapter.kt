package com.example.health2u.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.health2u.R
import com.example.health2u.model.Sources
import com.squareup.picasso.Picasso

class NewsListAdapter(
    private val list: ArrayList<Sources>,
    private val selectedListItem: (Sources) -> Unit
) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsListAdapter.ViewHolder, position: Int) {
        list.get(position).let { holder.bindView(it, selectedListItem, position) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindView(item: Sources, selectedListItem: (Sources) -> Unit, position: Int) {
            itemView.apply {

                if (!item.urlToImage.isNullOrEmpty()) {
                    Picasso.get().load(item.urlToImage).fit()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(findViewById<AppCompatImageView>(R.id.news_image));
                } else {
                    findViewById<AppCompatImageView>(R.id.news_image).setImageResource(R.drawable.ic_launcher_foreground)
                }

                findViewById<AppCompatTextView>(R.id.title_name).text = item.title
                findViewById<AppCompatTextView>(R.id.descrption).text = item.description
                findViewById<AppCompatTextView>(R.id.content).text = item.content
                findViewById<AppCompatTextView>(R.id.author).text = item.author

            }
            itemView.setOnClickListener { selectedListItem(item) }
        }

    }
}