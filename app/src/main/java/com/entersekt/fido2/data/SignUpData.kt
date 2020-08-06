package com.entersekt.fido2.data

data class SignUpData(
    val status: String,
    val statusCode: String,
    val transactionId: String,
    val registrationId: String,
    val userId: String,
    val aaid: String
)