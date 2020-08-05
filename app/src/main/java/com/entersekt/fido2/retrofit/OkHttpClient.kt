package com.entersekt.fido2.retrofit

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import org.apache.http.conn.ssl.SSLSocketFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


fun getUnsafeOkHttpClient(): OkHttpClient {
    return try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts: Array<TrustManager> =
            arrayOf<TrustManager>(object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOfNulls(0)
                }


            })

        // Install the all-trusting trust manager
        val sslContext: SSLContext = SSLContext.getInstance("TLS")
        sslContext.init(
            null, trustAllCerts,
            SecureRandom()
        )
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory: javax.net.ssl.SSLSocketFactory = sslContext
            .socketFactory
        var okHttpClient = OkHttpClient()
        okHttpClient = okHttpClient.newBuilder()
            .sslSocketFactory(sslSocketFactory)
            .hostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
            .build()
        okHttpClient
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}