package com.sdk.weatherapp.presentation.ui.maps

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.sdk.weatherapp.R
import com.sdk.weatherapp.data.util.toast
import com.sdk.weatherapp.databinding.FragmentMapsBinding
import com.sdk.weatherapp.presentation.activity.MainActivity

class MapsFragment : Fragment(), OnMapReadyCallback, LocationListener,
GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private var map: GoogleMap? = null
    private lateinit var lastLocation: Location
    private var locationMarker: Marker? = null
    private var googleApiClient: GoogleApiClient? = null
    private lateinit var locationRequest: LocationRequest
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.hide()
        editSearch()
    }

    private fun editSearch() {
        binding.editSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchLocation()
            }
            true
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0

        map?.setOnMapClickListener { ll ->
            onMapClick(ll)
        }

        val location = LatLng(55.7558, 37.6173)

        val markerOptions = MarkerOptions().position(location).title("Moscow, Russia")
        map?.addMarker(markerOptions)
        map?.animateCamera(CameraUpdateFactory.newLatLng(location))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                buildGoogleApiClient()
                map?.isMyLocationEnabled = true
            }
        } else {
            buildGoogleApiClient()
            map?.isMyLocationEnabled = true
        }
    }

    private fun buildGoogleApiClient() {
        googleApiClient = GoogleApiClient.Builder(requireContext())
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        googleApiClient?.connect()
    }

    override fun onLocationChanged(location: Location) {
        lastLocation = location
        if (locationMarker != null) {
            locationMarker?.remove()
        }
        val latLng = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.apply {
            position(latLng)
            title("Current Location")
            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        }
        locationMarker = map?.addMarker(markerOptions)
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))

        googleApiClient?.let {
            LocationServices.getFusedLocationProviderClient(requireContext())
        }
    }

    override fun onConnected(p0: Bundle?) {
        locationRequest = LocationRequest()
        locationRequest.interval = 1000
        locationRequest.fastestInterval = 1000
        locationRequest.priority = PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            LocationServices.getFusedLocationProviderClient(requireContext())
        }
    }

    override fun onConnectionSuspended(p0: Int) {}

    override fun onConnectionFailed(p0: ConnectionResult) {}

    private fun searchLocation() {
        hideKeyboard(requireActivity(), binding.editSearch)
        val location: String = binding.editSearch.text.toString().trim()
        var addressList: List<android.location.Address> = ArrayList()
        if (location.isNotEmpty()) {
            map?.clear()
            val geocoder = Geocoder(requireContext())
            try {
                addressList = geocoder.getFromLocationName(location, 1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val address = addressList[0]
            val latLng = LatLng(address.latitude, address.longitude)
            map?.addMarker(MarkerOptions().position(latLng).title(location))
            map?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        }
    }

    private fun onMapClick(ll: LatLng) {
        map?.clear()
        map?.addMarker(MarkerOptions().position(ll))
        map?.animateCamera(CameraUpdateFactory.newLatLng(ll))
    }

    private fun hideKeyboard(activity: Activity, viewToHide: View) {
        val imm = activity
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(viewToHide.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}