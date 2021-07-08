package com.example.health2u.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.health2u.BaseFragment
import com.example.health2u.R
import com.example.health2u.delegates.IBackHandlerDelegate
import com.healthcarelocator.model.config.HealthCareLocatorCustomObject
import com.healthcarelocator.state.HealthCareLocatorSDK

class MainActivity : AppCompatActivity(),IBackHandlerDelegate {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private var currentFragment: BaseFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupNavController()
//        val builder = HealthCareLocatorCustomObject.Builder()
//        builder.colorPrimary("#a6244d")
//        builder.colorSecondary("#36bfd4")
//        HealthCareLocatorSDK.init("2001302015b8ad9b").setAppName("Health2u")
//            .setAppDownloadLink("Your app download link").setCustomObject(builder.build())
//        HealthCareLocatorSDK.getInstance().startSDKActivity(this)
    }


    override fun onBackPressed() {
        if (currentFragment == null || !currentFragment!!.onBackPressed()) {
            super.onBackPressed()
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_container).navigateUp(appBarConfiguration)
    }

    private fun setupNavController() {
        val host: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
                ?: return
        navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, _, _ ->
            //hidekeyboard()
        }
    }

    override fun setCurrentFragment(baseFragment: BaseFragment) {
        currentFragment=baseFragment
    }
}