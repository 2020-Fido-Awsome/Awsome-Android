package com.entersekt.fido2.data

data class ResponseData(
    val publicKeyCredentialCreationOptions: First,
    val status: String,
    val statusCode: String
)

data class First(
    val attestation: String,
    val challenge: String,
    val user: User,
    val rp: Rp,
    val timeout: Int,
    val pubKeyCredParams: ArrayList<PubKey>
)

data class User(
    val displayName: String,
    val name: String,
    val id: String
)

data class Rp(
    val name: String,
    val id: String
)

data class PubKey
    (
    val type: String,
    val alg: Int
)


