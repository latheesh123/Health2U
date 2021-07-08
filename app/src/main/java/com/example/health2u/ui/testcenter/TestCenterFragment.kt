package com.example.health2u.ui.testcenter

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.health2u.R
import com.example.health2u.adapter.NewsListAdapter
import com.example.health2u.adapter.TestingCenterListAdapter
import com.example.health2u.model.Centers
import com.example.health2u.repo.Injection
import com.example.health2u.ui.NewsViewModel
import com.example.health2u.utils.CommonViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class TestCenterFragment : Fragment() {

    private lateinit var client: FusedLocationProviderClient
    private lateinit var getnearBysites: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var locationManger: LocationManager
    private var latitude: String = ""
    private var longitude: String = ""
    private var testingCenterListAdapter: TestingCenterListAdapter? = null

    companion object {
        fun newInstance() = TestCenterFragment()
    }

    private val testCenterViewModel: TestCenterViewModel by activityViewModels {
        CommonViewModelFactory(Injection.getRepositoryImpl())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        client = activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()

        } else {

            requestPermissionFor(201, this)
        }
        return inflater.inflate(R.layout.test_center_fragment, container, false)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 201) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.center_recycler_view)

        getnearBysites = view.findViewById(R.id.near_by_list)
        getnearBysites.setOnClickListener {
            getCurrentLocation()
            testCenterViewModel.getCentersList(latitude + "," + longitude)

        }

        testCenterViewModel.getCentersList(latitude + "," + longitude)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        testCenterViewModel.centerslist.observe(viewLifecycleOwner, Observer {
            testingCenterListAdapter = TestingCenterListAdapter(it, ::itemClicked)
            recyclerView.adapter = testingCenterListAdapter

        })
    }

    private fun itemClicked(centers: Centers) {

        val gmmIntentUri = Uri.parse("google.navigation:q="+centers.position?.lat.toString()+","+centers.position?.lng.toString())
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
      startActivity(mapIntent)
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        locationManger = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManger.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManger.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        ) {
            client.lastLocation.addOnCompleteListener {
                val location: Location? = it.result
                latitude = location?.latitude.toString()
                longitude = location?.longitude.toString()
            }

            return

        }

    }

    @Throws(Exception::class)
    fun requestPermissionFor(permissionRequestCode: Int, fragment: Fragment) {
        try {
            fragment.requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                permissionRequestCode
            )
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

}