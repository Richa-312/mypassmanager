package com.example.mypassmanagerapp.utils

import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

class BiometricHelper(
    private val activity: FragmentActivity,
    private val onSuccess: () -> Unit,
    private val onError: (error: String) -> Unit = {}
) {
    private val executor = ContextCompat.getMainExecutor(activity)

    private val promptInfo = BiometricPrompt.PromptInfo.Builder()
        //.setTitle("Unlock MyPassManager")
        //.setSubtitle("Confirm your identity")
        //.setNegativeButtonText("Use PIN instead")
        .setAllowedAuthenticators(
            BiometricManager.Authenticators.BIOMETRIC_STRONG /*or
                    BiometricManager.Authenticators.DEVICE_CREDENTIAL*/
        )
        .build()

    private val biometricPrompt = BiometricPrompt(
        activity,
        executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Log.d("BIO", "onAuthenticationSucceeded")
                activity.runOnUiThread { onSuccess() }
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Log.e("BIO", "onAuthenticationError ($errorCode): $errString")
                activity.runOnUiThread { onError(errString.toString()) }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.w("BIO", "onAuthenticationFailed: biometric not recognized")
            }
        }
    )


    fun authenticate() {
        when (BiometricManager.from(activity).canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                    BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                biometricPrompt.authenticate(promptInfo)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                onError("No biometric hardware available")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                onError("No biometrics enrolled on this device")
            else ->
                onError("Biometric authentication unavailable")
        }
    }
}

