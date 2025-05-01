package com.example.mypassmanagerapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import android.widget.Toast
import com.example.mypassmanagerapp.utils.BiometricHelper

@Composable
fun AuthScreen(navController: NavController) {
    val context = LocalContext.current
    val activity = context as? FragmentActivity
    val errorState = remember { mutableStateOf<String?>(null) }

    // Trigger biometric auth once
    LaunchedEffect(Unit) {
        if (activity != null) {
            val biometricHelper = BiometricHelper(
                activity,
                onSuccess = {
                    navController.navigate("password_list_screen") {
                        popUpTo("auth_screen") { inclusive = true }
                    }
                },
                onError = { errorMsg ->
                    errorState.value = errorMsg
                }
            )
            biometricHelper.authenticate()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (errorState.value != null) {
            // Show error and maybe fallback
            Toast.makeText(context, errorState.value, Toast.LENGTH_LONG).show()
            Text(
                text = "Authentication error: ${errorState.value}.\nPlease use PIN.",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            Text("Authenticating with Biometrics...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
