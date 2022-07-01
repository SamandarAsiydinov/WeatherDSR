package com.sdk.weatherapp.data.util

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sdk.weatherapp.data.util.Constants.TAG

fun Fragment.toast(text: String) {
    Toast.makeText(this.requireContext(), text, Toast.LENGTH_SHORT).show()
}
fun Fragment.log(msg: String) {
    Log.d(TAG, msg)
}