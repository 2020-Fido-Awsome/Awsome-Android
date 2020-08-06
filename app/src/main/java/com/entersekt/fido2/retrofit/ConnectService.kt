package com.entersekt.fido2.retrofit

import com.entersekt.fido2.data.ResponseData
import com.entersekt.fido2.data.SignUpData
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface ConnectService {

    @POST("/api/v1/fido2/attestation/options")
    fun postSignUp(@Body jsonObject: JsonObject ): Call<ResponseData>

    @POST("/api/v1/fido2/attestation/result")
    fun postResult(@Body jsonObject: JsonObject): Call<SignUpData>

}
