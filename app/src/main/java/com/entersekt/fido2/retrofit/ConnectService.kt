package com.entersekt.fido2.retrofit

import com.entersekt.fido2.data.DefaultData
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface ConnectService {

    @POST("/api/v1/fido2/attestation/options")
//    fun getSignIn(@Body jsonObject: JsonObject): Call<DefaultData>
    fun getSignIn(@Body jsonObject: JsonObject ): Call<DefaultData>


}
