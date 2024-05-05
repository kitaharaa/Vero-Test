package com.kitaharaa.digitalapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Context.isCameraPermissionGranted(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
    val cameraPermission = Manifest.permission.CAMERA
    val hasPermission = ContextCompat.checkSelfPermission(
        this,
        cameraPermission
    ) == PackageManager.PERMISSION_GRANTED

    if(hasPermission) onSuccess() else onFailure(cameraPermission)
}