package com.entersekt.fido2.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConnectServiceImpl{
    private val BASE_URL =  "https://aws.eazysecure-auth.com/"//HOST URL
    private var retrofit : Retrofit
    //    private var okHttpClient : OkHttpClient
    private var gson : Gson = GsonBuilder().setLenient().create()

    init{

        //
//        okHttpClient = OkHttpClient().newBuilder().apply {
//            connectTimeout(TIMEOUT, TimeUnit.SECONDS)
//            writeTimeout(TIMEOUT, TimeUnit.SECONDS)
//            readTimeout(TIMEOUT, TimeUnit.SECONDS)
//            addInterceptor(logging)
//        }.build()

        retrofit = Retrofit.Builder().apply {
            baseUrl(BASE_URL)
//            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create(gson))
        }.build()
    }

    val service: ConnectService = retrofit.create(ConnectService::class.java)

}