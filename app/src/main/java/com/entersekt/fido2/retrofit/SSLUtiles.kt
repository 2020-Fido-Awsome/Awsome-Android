package com.entersekt.fido2.retrofit

import android.content.Context
import android.util.Log
import com.entersekt.fido2.R
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.security.*
import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory


object SSLUtiles {

    fun getPinnedCertSslSocketFactory(context: Context): SSLSocketFactory? {
        try {
            val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
            val caInput: InputStream = context.resources.openRawResource(R.raw.cert)
            var ca: Certificate? = null
            try {
                ca = cf.generateCertificate(caInput)
                System.out.println(
                    "ca=" + ((ca as X509Certificate?)?.getSubjectDN() ?: "아무것도 아니다,,")
                )
            } catch (e: CertificateException) {
                e.printStackTrace()
            } finally {
                caInput.close()
            }
            val keyStoreType: String = KeyStore.getDefaultType()
            val keyStore: KeyStore = KeyStore.getInstance(keyStoreType)
            keyStore.load(null, null)
            if (ca == null) {
                return null
            }
            keyStore.setCertificateEntry("ca", ca)
            val tmfAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
            val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm)
            tmf.init(keyStore)
            val sslContext: SSLContext = SSLContext.getInstance("TLS")
            sslContext.init(null, tmf.getTrustManagers(), null)
            return sslContext.getSocketFactory()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getSslContextForCertificateFile(fileName: String?): SSLContext? {
        return try {
            val keyStore: KeyStore? = fileName?.let { SSLUtiles.getKeyStore(it) }
            val sslContext = SSLContext.getInstance("SSL")
            val trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)
            sslContext.init(null, trustManagerFactory.trustManagers, SecureRandom())
            sslContext
        } catch (e: java.lang.Exception) {
            val msg = "Cannot load certificate from file"
            Log.e(msg, e.toString())
            throw RuntimeException(msg)
        }
    }

    private fun getKeyStore(fileName: String): KeyStore? {
        var keyStore: KeyStore? = null
        try {
            val cf =
                CertificateFactory.getInstance("X.509")
            val inputStream: InputStream = FileInputStream(fileName)
            val ca: Certificate
            try {
                ca = cf.generateCertificate(inputStream)
                Log.d("ca={}", (ca as X509Certificate).subjectDN.toString())
            } finally {
                inputStream.close()
            }
            val keyStoreType = KeyStore.getDefaultType()
            keyStore = KeyStore.getInstance(keyStoreType)
            keyStore.load(null, null)
            keyStore.setCertificateEntry("ca", ca)
        } catch (e: java.lang.Exception) {
            Log.e("Error during getting keystore", e.toString())
        }
        return keyStore
    }
}