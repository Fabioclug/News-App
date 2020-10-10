package com.fclug.newsapp.feed

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.fclug.newsapp.R
import com.fclug.newsapp.adapter.NewsAdapter
import com.fclug.newsapp.databinding.FragmentFeedBinding
import com.fclug.newsapp.model.Article
import com.fclug.newsapp.util.ARTICLE_ARGUMENT
import kotlinx.android.synthetic.main.fragment_feed.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment : Fragment() {

    private val feedViewModel: FeedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentFeedBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNewsList()
        observeNewsList()
        observeRefresh()
    }

    private fun setupNewsList() {
        newsList.adapter = NewsAdapter(::openArticle)
    }

    private fun observeNewsList() {
        feedViewModel.articles.observe(viewLifecycleOwner, Observer {pagedListLiveData ->
            pagedListLiveData.observe(viewLifecycleOwner, Observer {
                (newsList.adapter as NewsAdapter).submitList(it)
            })
        })
    }

    private fun observeRefresh() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>("refresh")
            ?.observe(viewLifecycleOwner, Observer {
                if(it) {
                    feedViewModel.loadArticlesList()
                }
            })
    }

    private fun openArticle(article: Article) {
        val args = Bundle()
        args.putParcelable(ARTICLE_ARGUMENT, article)
        findNavController().navigate(R.id.action_feedFragment_to_detailsFragment, args)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_feed, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                findNavController().navigate(R.id.action_feedFragment_to_settingsFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
