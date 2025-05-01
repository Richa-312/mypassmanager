package com.example.passwordmanagerapp.utils

import android.content.Context
import android.util.Base64
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object EncryptionUtil {

    fun encrypt(context: Context, text: String): String {
        val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val encryptedSharedPreferences = EncryptedSharedPreferences.create(
            context,
            "secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val encrypted = Base64.encodeToString(text.toByteArray(), Base64.DEFAULT)
        return encrypted
    }

    fun decrypt(encryptedText: String): String {
        val decodedBytes = Base64.decode(encryptedText, Base64.DEFAULT)
        return String(decodedBytes)
    }
}
