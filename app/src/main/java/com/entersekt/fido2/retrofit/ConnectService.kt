package com.entersekt.fido2.retrofit

import com.entersekt.fido2.data.DefaultData
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface ConnectService {

    @POST("/api/v1/user/register")
    fun getSignIn(body: JsonObject): Call<DefaultData>
}
