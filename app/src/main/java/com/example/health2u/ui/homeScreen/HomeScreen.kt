package com.example.health2u.ui.homeScreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.health2u.R
import com.example.health2u.ui.MainActivity
import com.healthcarelocator.model.map.*

import com.example.health2u.ui.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.healthcarelocator.model.SearchObject
import com.healthcarelocator.model.config.HCLQueryObject
import com.healthcarelocator.model.config.HealthCareLocatorCustomObject
import com.healthcarelocator.state.HealthCareLocatorSDK

class HomeScreen : Fragment() {

    private lateinit var emergency_button: ImageView
    private lateinit var provider_button: ImageView
    private lateinit var vaccine_button: ImageView
    private lateinit var guide_button: ImageView
    private lateinit var symptoms_button: ImageView
    private lateinit var tracking_button: ImageView
    private lateinit var testing_button: ImageView
    private lateinit var settings_button: ImageView

    var emergencyNumber: String = ""

    companion object {
        fun newInstance() = HomeScreen()
    }

    private lateinit var viewModel: HomeScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_screen_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeScreenViewModel::class.java)

        emergency_button = view.findViewById(R.id.emergency_button)
        provider_button = view.findViewById(R.id.health_care_providers)
        vaccine_button = view.findViewById(R.id.vaccine_centers)
        guide_button = view.findViewById(R.id.covid_health_guide)
        symptoms_button = view.findViewById(R.id.symptopms)
        tracking_button = view.findViewById(R.id.health_tracking)
        testing_button = view.findViewById(R.id.testing)
        settings_button = view.findViewById(R.id.settings)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        emergencyNumber= sharedPref.getString("phone_number", "911").toString()

        emergency_button.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:"+emergencyNumber)
            startActivity(intent)
        }
        vaccine_button.setOnClickListener {

findNavController().navigate(R.id.destination_vaccine_center)
        }
        guide_button.setOnClickListener { findNavController().navigate(R.id.destination_guide) }
        symptoms_button.setOnClickListener { }
        tracking_button.setOnClickListener { }
        testing_button.setOnClickListener { findNavController().navigate(R.id.destination_test_centers) }
        settings_button.setOnClickListener { findNavController().navigate(R.id.destination_settings) }


        provider_button.setOnClickListener {

            val builder = HealthCareLocatorCustomObject.Builder()
            builder.colorPrimary("#a6244d")
            builder.colorSecondary("#36bfd4")
            HealthCareLocatorSDK.init("2001302015b8ad9b").setAppName("Health2u")
                .setAppDownloadLink("Your app download link").setCustomObject(builder.build())
            HealthCareLocatorSDK.getInstance().startSDKActivity(requireActivity() as MainActivity)

        }
    }
}