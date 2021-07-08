package com.example.health2u.ui

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.health2u.R
import com.example.health2u.adapter.NewsListAdapter
import com.example.health2u.model.Sources
import com.example.health2u.repo.Injection
import com.example.health2u.utils.CommonViewModelFactory

class NewsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    private var newsListAdapter:NewsListAdapter?=null

    companion object {
        fun newInstance() = NewsFragment()
    }


    private val newsViewModel: NewsViewModel by activityViewModels {
        CommonViewModelFactory(Injection.getRepositoryImpl())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView=view.findViewById(R.id.news_recycler_view)
        newsViewModel.getNewsList()
        
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        newsViewModel.newslist.observe(viewLifecycleOwner, Observer {
            newsListAdapter= NewsListAdapter(it,::itemClicked)
            recyclerView.adapter=newsListAdapter

        })
    }

    private fun itemClicked(sources: Sources) {

        val intent=Intent(Intent.ACTION_VIEW, Uri.parse(sources.url))
        startActivity(intent)

    }
}