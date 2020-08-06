package com.entersekt.fido2.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.SSLContext


object ConnectServiceImpl {
    private val BASE_URL = "https://aws.eazysecure-auth.com/"//HOST URL
    private var retrofit: Retrofit

    //    private var okHttpClient : OkHttpClient
    private var gson: Gson = GsonBuilder().setLenient().create()
//
//    val urlConnection: URLConnection = url.openConnection()
//    val inputStream: InputStream = urlConnection.getInputStream()
//    copyInputStreamToOutputStream(inputStream, System.out)

    init {
        val sslContext: SSLContext? =
            SSLUtiles.getSslContextForCertificateFile("cert.crt")

        retrofit = Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            client(getUnsafeOkHttpClient())

//            if (sslContext != null) {
//                client(
//                    OkHttpClient.Builder()
//                        .sslSocketFactory(sslContext.socketFactory)   //ssl
//                        .build()
//                )
//            }
            addConverterFactory(GsonConverterFactory.create(gson))
        }.build()
    }

    val service: ConnectService = retrofit.create(ConnectService::class.java)

}


