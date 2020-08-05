package com.entersekt.fido2.appdata

import android.app.Application
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class AwsomeApp :Application(){
    companion object{

        lateinit var  globalApplication: AwsomeApp
        lateinit var instance : AwsomeApp

        fun getGlobalApplicationContext(): AwsomeApp {
            return instance
        }

        lateinit var prefs : AwsomeSharedPreferences


        @RequiresApi(Build.VERSION_CODES.M)
        fun genKey() {

            val keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                "awsKey",
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()

            keyGenerator.init(keyGenParameterSpec)
            keyGenerator.generateKey()
        }

        fun getKey(): SecretKey {
            val keystore = java.security.KeyStore.getInstance("AndroidKeyStore")
            keystore.load(null)

            val secretKeyEntry = keystore.getEntry("awsKey", null) as java.security.KeyStore.SecretKeyEntry
            return secretKeyEntry.secretKey
        }

        fun encryptData(data: String): Pair<ByteArray, ByteArray> {
            val cipher = Cipher.getInstance("AES/CBC/NoPadding")

            var temp = data
            while (temp.toByteArray().size % 16 != 0)
                temp += "\u0020"

            cipher.init(Cipher.ENCRYPT_MODE, getKey())

            val ivBytes = cipher.iv
            val encryptedBytes = cipher.doFinal(temp.toByteArray(Charsets.UTF_8))

            return Pair(ivBytes, encryptedBytes)
        }

        fun decryptData(ivBytes: ByteArray, data: ByteArray): String {
            val cipher = Cipher.getInstance("AES/CBC/NoPadding")
            val spec = IvParameterSpec(ivBytes)

            cipher.init(Cipher.DECRYPT_MODE, getKey(), spec)
            return cipher.doFinal(data).toString(Charsets.UTF_8).trim()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate() {
        prefs = AwsomeSharedPreferences(
            applicationContext
        )
        super.onCreate()

        prefs.checkFirstRun()
        DataManage.init(this)
    }
}