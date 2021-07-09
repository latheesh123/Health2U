package com.example.health2u.ui.vaccinecenter

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.example.health2u.R
import com.healthcarelocator.model.HCLLocation

class VacccineFragment : Fragment() {

    companion object {
        fun newInstance() = VacccineFragment()
    }

    private lateinit var viewModel: VacccineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.vacccine_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myWebView: WebView = view.findViewById(R.id.webview)
        myWebView.settings.javaScriptEnabled = true

        myWebView.loadUrl("https://www.vaccines.gov/search/")

    }

}