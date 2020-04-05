package com.fclug.newsapp.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController

import com.fclug.newsapp.R
import com.fclug.newsapp.databinding.FragmentDetailsBinding
import com.fclug.newsapp.model.Article
import com.fclug.newsapp.util.loadImage
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.android.ext.android.inject

class DetailsFragment : Fragment() {

    private val detailsViewModel: DetailsViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.detailsViewModel = detailsViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {args ->
            val article: Article? = args.getParcelable("article")
            article?.let {
                detailsViewModel.updateArticle(it)
                image.loadImage(it.urlToImage)
                setupLink()
                Log.d("article: ", it.toString())
            }
        }
    }

    private fun setupLink() {
        link.setOnClickListener {
            val args = Bundle()
            args.putString("url", detailsViewModel.currentArticle.value?.url)
            findNavController().navigate(R.id.action_detailsFragment_to_browserFragment, args)
        }
    }
}
