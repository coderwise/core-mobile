package com.coderwise.core.permissions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.coderwise.core.permissions.di.AndroidPermissionHelper
import com.coderwise.core.permissions.impl.asAndroidPermissionId
import org.koin.compose.koinInject

@Composable
actual fun ProcessPermissionRequestEffect(
    permission: Permission
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val permissionService = koinInject<PermissionService>()

    val context = LocalContext.current
    val androidPermissionHelper = koinInject<AndroidPermissionHelper>()

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
            permissionService.checkPermission(permission)
        }

    LaunchedEffect(permission) {
        androidPermissionHelper.setShowRationale {
            val androidPermissionId = it.asAndroidPermissionId()
            val should = context.findActivity().shouldShowRationale(androidPermissionId)
            should
        }

        permissionService.requests.collect {
            if (it == permission) {
                launcher.launch(
                    permission.asAndroidPermissionId()
                )
            }
        }
    }

    val scope = rememberCoroutineScope()

    DisposableEffect(permission, lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                permissionService.checkPermission(permission)
//                scope.launch {
//                    permissionService.requestPermission(permission)
//                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

internal fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}

internal fun Activity.shouldShowRationale(permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
}
