package com.fclug.newsapp.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            feedViewModel.updateCountry("ca")
        }
        setupNewsList()
        observeNewsList()
    }

    private fun setupNewsList() {
        newsList.adapter = NewsAdapter(::openArticle)
    }

    private fun observeNewsList() {
        feedViewModel.articles.observe(viewLifecycleOwner, Observer { pagedListLiveData ->
            pagedListLiveData.observe(viewLifecycleOwner, Observer {
                (newsList.adapter as NewsAdapter).submitList(it)
            })
        })
    }

    private fun openArticle(article: Article) {
        val args = Bundle()
        args.putParcelable("article", article)
        findNavController().navigate(R.id.action_feedFragment_to_detailsFragment, args)
    }

}
