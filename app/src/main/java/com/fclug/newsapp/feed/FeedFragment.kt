package com.fclug.newsapp.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.fclug.newsapp.R
import com.fclug.newsapp.adapter.NewsAdapter
import com.fclug.newsapp.model.Article
import kotlinx.android.synthetic.main.fragment_feed.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment : Fragment() {

    private val feedViewModel: FeedViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupNewsList()
        observeNewsList()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupNewsList() {
        newsList.adapter = NewsAdapter(::openArticle)
    }

    private fun observeNewsList() {
        feedViewModel.getArticles().observe(viewLifecycleOwner, Observer {
            (newsList.adapter as NewsAdapter).loadItems(it)
        })
    }

    private fun openArticle(article: Article) {
        val args = Bundle()
        args.putParcelable("article", article)
        findNavController().navigate(R.id.action_feedFragment_to_detailsFragment, args)
    }

}
