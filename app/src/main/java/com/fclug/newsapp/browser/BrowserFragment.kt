package com.fclug.newsapp.browser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.fclug.newsapp.R
import com.fclug.newsapp.util.URL_ARGUMENT
import kotlinx.android.synthetic.main.fragment_browser.*

class BrowserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_browser, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {args ->
            val url: String? = args.getString(URL_ARGUMENT)
            url?.let {
                articleViewer.webViewClient = WebViewClient()
                articleViewer.settings.javaScriptEnabled = true
                articleViewer.loadUrl(it)
            }
        }
    }
}
