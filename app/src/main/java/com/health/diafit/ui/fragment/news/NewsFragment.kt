package com.health.diafit.ui.fragment.news

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.health.diafit.R
import com.health.diafit.databinding.FragmentNewsBinding
import com.health.diafit.data.remote.response.WebLink
import com.health.diafit.databinding.FragmentNewsDiaBinding
import com.health.diafit.ui.activity.main.MainActivity
import com.health.diafit.util.ProgressDialog

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsDiaBinding? = null
    private val binding get() = _binding!!
    private val args: NewsFragmentArgs by navArgs()
    private val viewModel by viewModels<NewsViewModel>()
    private val progressDialog by lazy { ProgressDialog(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDiaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arg = args.news

        setupToolbar()

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) progressDialog.showLoading()
            else progressDialog.hideLoading()
        }

        setupWebView(arg)
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.isTitleCentered = true
        toolbar.setNavigationIcon(R.drawable.ic_arrow_30)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupWebView(args: WebLink) {
        val webView = binding.webView
        webView.settings.apply {
            domStorageEnabled = true
            allowFileAccess = false
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                viewModel.hideLoading()
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                viewModel.showLoading()
            }
        }
        webView.loadUrl(args.url)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}