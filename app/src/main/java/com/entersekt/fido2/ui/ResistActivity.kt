package com.entersekt.fido2

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.google.android.gms.fido.Fido
import com.google.android.gms.fido.fido2.api.common.*
import kotlinx.android.synthetic.main.activity_resist.*
import java.security.SecureRandom


class ResistActivity : AppCompatActivity() {
    companion object {
        private const val LOG_TAG = "Fido2Demo"
        private const val REQUEST_CODE_REGISTER = 1
        private const val REQUEST_CODE_SIGN = 2
        private const val KEY_HANDLE_PREF = "key_handle"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resist)
        registerFido2()
        //signFido2Button.setOnClickListener { signFido2() }
        //signFido2Button.isEnabled = loadKeyHandle() != null

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(LOG_TAG, "onActivityResult - requestCode: $requestCode, resultCode: $resultCode")

        when (resultCode) {
            RESULT_OK -> {
                data?.let {
                    if (it.hasExtra(Fido.FIDO2_KEY_ERROR_EXTRA)) {
                        handleErrorResponse(data.getByteArrayExtra(Fido.FIDO2_KEY_ERROR_EXTRA))
                    } else if (it.hasExtra(Fido.FIDO2_KEY_RESPONSE_EXTRA)) {
                        val fido2Response = data.getByteArrayExtra(Fido.FIDO2_KEY_RESPONSE_EXTRA)
                        when (requestCode) {
                            REQUEST_CODE_REGISTER -> handleRegisterResponse(fido2Response)
                            REQUEST_CODE_SIGN -> handleSignResponse(fido2Response)
                        }
                    }
                }
            }
            RESULT_CANCELED -> {
                val result = "Operation is cancelled"
                resultText.text = result
                Log.d(LOG_TAG, result)

            }
            else -> {
                val result = "Operation failed, with resultCode: $resultCode"
                resultText.text = result
                Log.e(LOG_TAG, result)
            }
        }
    }

    private fun registerFido2() {
        // All the option parameters should come from the Relying Party / server
        Log.e("registerFido2 실행실행", "실행 시작")
        val options = PublicKeyCredentialCreationOptions.Builder()
            .setRp(PublicKeyCredentialRpEntity("aws.eazysecure-auth.com", "Fido2Demo", null))
            .setUser(
                PublicKeyCredentialUserEntity(
                    "0803".toByteArray(),
                    "0803",
                    "0803",
                    "0803"
                )
            )
            .setChallenge(challenge())
            .setParameters(
                listOf(
                    PublicKeyCredentialParameters(
                        PublicKeyCredentialType.PUBLIC_KEY.toString(),
                        EC2Algorithm.ES256.algoValue
                    )
                )
            )
            .build()

        val fido2ApiClient = Fido.getFido2ApiClient(applicationContext)
        val fido2PendingIntentTask = fido2ApiClient.getRegisterIntent(options) // getRegisterPendingIntent

//        fido2PendingIntentTask.addOnSuccessListener (
//             OnSuccessListener() {
//                @Override
//                 fun onSuccess(pendingIntent : PendingIntent) {
//                    if (pendingIntent != null) {
//                        // Start a FIDO2 registration request.
//                        Log.e("fido2PendingIntent","실행")
//                        val activity = ResistActivity()
//                        activity.startIntentSenderForResult(
//                            pendingIntent.intentSender,
//                            REQUEST_CODE_REGISTER,
//                            null, // fillInIntent,
//                            0, // flagsMask,
//                            0, // flagsValue,
//                            0 //extraFlags);
//                        )
//                    }
//                }
//            })
//        fido2PendingIntentTask.addOnFailureListener(
//             OnFailureListener() {
//                @Override
//                fun onFailure(e: Exception) {
//                    // Fail
//                }
//            }
//        )

        fido2PendingIntentTask.addOnSuccessListener { fido2PendingIntent ->
            if (fido2PendingIntent.hasPendingIntent()) {
                try {
                    Log.e("실행되라멍청아", "launching Fido2 Pending Intent")
                    fido2PendingIntent.launchPendingIntent(this@ResistActivity, REQUEST_CODE_REGISTER)
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }
    }


    private fun signFido2() {
        // All the option parameters should come from the Relying Party / server
        val options = PublicKeyCredentialRequestOptions.Builder()
            .setRpId("aws.eazysecure-auth.com")
            .setAllowList(
                listOf(
                    PublicKeyCredentialDescriptor(
                        PublicKeyCredentialType.PUBLIC_KEY.toString(),
                        loadKeyHandle(),
                        null
                    )
                )
            )
            .setChallenge(challenge())
            .build()

        val fido2ApiClient = Fido.getFido2ApiClient(applicationContext)
        val fido2PendingIntentTask = fido2ApiClient.getSignIntent(options)
        fido2PendingIntentTask.addOnSuccessListener { fido2PendingIntent ->
            if (fido2PendingIntent.hasPendingIntent()) {
                try {
                    Log.e(LOG_TAG, "launching Fido2 Pending Intent")
                    fido2PendingIntent.launchPendingIntent(this@ResistActivity, REQUEST_CODE_SIGN)
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun handleErrorResponse(errorBytes: ByteArray) {
        val authenticatorErrorResponse = AuthenticatorErrorResponse.deserializeFromBytes(errorBytes)
        val errorName = authenticatorErrorResponse.errorCode.name
        val errorMessage = authenticatorErrorResponse.errorMessage

        Log.e(LOG_TAG, "errorCode.name: $errorName")
        Log.e(LOG_TAG, "errorMessage: $errorMessage")

        val registerFidoResult = "An Error Ocurred\n\nError Name:\n$errorName\n\nError Message:\n$errorMessage"

        //원래 코드 : resultText.text = registerFidoResult

        resultText.text = registerFidoResult



    }

    /**
     * The response should be sent to the Relying Party / server to validate and store
     */
    private fun handleRegisterResponse(fido2Response: ByteArray) {
        val response = AuthenticatorAttestationResponse.deserializeFromBytes(fido2Response)
        Log.e(LOG_TAG, "응답바디: $response")
        val keyHandleBase64 = Base64.encodeToString(response.keyHandle, Base64.DEFAULT)
        val clientDataJson = String(response.clientDataJSON, Charsets.UTF_8)
        val attestationObjectBase64 = Base64.encodeToString(response.attestationObject, Base64.DEFAULT)

        storeKeyHandle(response.keyHandle)
        //signFido2Button.isEnabled = true

        Log.e(LOG_TAG, "keyHandleBase64: $keyHandleBase64")
        Log.e(LOG_TAG, "clientDataJSON: $clientDataJson")
        Log.e(LOG_TAG, "attestationObjectBase64: $attestationObjectBase64")

        val registerFido2Result = "Authenticator Attestation Response\n\n" +
                "keyHandleBase64:\n" +
                "$keyHandleBase64\n\n" +
                "clientDataJSON:\n" +
                "$clientDataJson\n\n" +
                "attestationObjectBase64:\n" +
                "$attestationObjectBase64\n"

        //회원가입 성공-소켓 통신 호출
        SignupActivity.StoreConnect(intent.getStringExtra("nick")).start()

        resultText.text = registerFido2Result
        Toast.makeText(this, "회원가입 성공했습니다", Toast.LENGTH_LONG).show()
        var intent = Intent(this, StartActivity::class.java);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent)
        finish()
    }

    /**
     * The response should be sent to the Relying Party / server to validate
     */
    private fun handleSignResponse(fido2Response: ByteArray) {
        val response = AuthenticatorAssertionResponse.deserializeFromBytes(fido2Response)
        val keyHandleBase64 = Base64.encodeToString(response.keyHandle, Base64.DEFAULT)
        val clientDataJson = String(response.clientDataJSON, Charsets.UTF_8)
        val authenticatorDataBase64 = Base64.encodeToString(response.authenticatorData, Base64.DEFAULT)
        val signatureBase64 = Base64.encodeToString(response.signature, Base64.DEFAULT)

        Log.d(LOG_TAG, "keyHandleBase64: $keyHandleBase64")
        Log.d(LOG_TAG, "clientDataJSON: $clientDataJson")
        Log.d(LOG_TAG, "authenticatorDataBase64: $authenticatorDataBase64")
        Log.d(LOG_TAG, "signatureBase64: $signatureBase64")

        val signFido2Result = "Authenticator Assertion Response\n\n" +
                "keyHandleBase64:\n" +
                "$keyHandleBase64\n\n" +
                "clientDataJSON:\n" +
                "$clientDataJson\n\n" +
                "authenticatorDataBase64:\n" +
                "$authenticatorDataBase64\n\n" +
                "signatureBase64:\n" +
                "$signatureBase64\n"

        resultText.text = signFido2Result

    }

    /**
     * https://www.w3.org/TR/webauthn/#cryptographic-challenges
     */
    private fun challenge(): ByteArray {
        val secureRandom = SecureRandom()
        val challenge = ByteArray(16)
        secureRandom.nextBytes(challenge)
        return challenge
    }

    private fun storeKeyHandle(keyHandle: ByteArray) {
        PreferenceManager.getDefaultSharedPreferences(this).edit {
            putString(KEY_HANDLE_PREF, Base64.encodeToString(keyHandle, Base64.DEFAULT))
        }
    }

    private fun loadKeyHandle(): ByteArray? {
        val keyHandleBase64 = PreferenceManager.getDefaultSharedPreferences(this).getString(KEY_HANDLE_PREF, null)
            ?: return null
        return Base64.decode(keyHandleBase64, Base64.DEFAULT)
    }
}
