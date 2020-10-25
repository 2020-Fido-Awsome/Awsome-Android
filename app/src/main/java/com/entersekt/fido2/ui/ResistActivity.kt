package com.entersekt.fido2.ui

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.entersekt.fido2.R
import com.entersekt.fido2.appdata.DataManage
import com.entersekt.fido2.data.ResponseData
import com.entersekt.fido2.data.SignUpData
import com.entersekt.fido2.retrofit.ConnectServiceImpl
import com.entersekt.fido2.ui.activity_tutorial.TutorialActivity
import com.google.android.gms.fido.Fido
import com.google.android.gms.fido.fido2.api.common.*
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_resist.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.security.SecureRandom



@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ResistActivity : AppCompatActivity() {
    companion object {
        private const val LOG_TAG = "awsome2020"
        private const val REQUEST_CODE_REGISTER = 1
        private const val REQUEST_CODE_SIGN = 2
        private const val KEY_HANDLE_PREF = "key_handle"

        var socket = Socket()
        lateinit var writeSocket: DataOutputStream
        lateinit var readSocket: DataInputStream
        var ip = "192.168.0.254"  //서버 ip
        var port = 9999
        var msg = "0"
        var nick = "defaultNickName"
        lateinit var responseData: ResponseData


    }

    lateinit var challenge:ByteArray


    var editor = DataManage.pref.edit()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resist)
        connect()
        //signFido2Button.setOnClickListener { signFido2() }
        //signFido2Button.isEnabled = loadKeyHandle() != null

//        nick = intent.getStringExtra("nick")!!
//        println("등록 실행시 : $nick")

//        SigninConnect(nick).start() // 0823 추가

//        val intent = Intent(this, MainActivity::class.java) //0823추가
//        startActivity(intent)
//        finish()
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

    //Retrofit 통신 시작
    fun connect() {
        Log.e("Retrofit 통신 시작", "")

        val ConnectJsonData = JSONObject()

        ConnectJsonData.put("userId", "")
        ConnectJsonData.put("deviceId", null)
        ConnectJsonData.put("transactionId", null)
        ConnectJsonData.put("username", "jihyeontest3")
        ConnectJsonData.put("displayName", "1111")
        ConnectJsonData.put("authenticatorSelection", null)
        ConnectJsonData.put("attestation", null)
        ConnectJsonData.put("userIcon", null)
        ConnectJsonData.put("extension", null)
        ConnectJsonData.put("secondFactor", false)

        val body = JsonParser.parseString(ConnectJsonData.toString()) as JsonObject

        ConnectServiceImpl.service.postSignUp(body).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                //통신 실패
                Log.e("통신 실패", "통신 실패닷 이눔아")
                Log.e("실패 이유가 뭐냐", t.toString())
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                //통신은 어쩌고 성공
                Log.e("통신 성공", "통신은 어쩌고 성공 ${response.body()}")
                responseData = response.body()!!

                registerFido2() // 0823삭제
            }
        })
    }


    fun registerFido2() {
        // All the option parameters should come from the Relying Party / server
        Log.e("registerFido2 실행실행", "실행 시작")

        challenge = challenge()
        println("challenge함수 값 $challenge")
        val options = PublicKeyCredentialCreationOptions.Builder()
            .setRp(PublicKeyCredentialRpEntity("aws.eazysecure-auth.com", "awsome2020", null))
            .setUser(
                PublicKeyCredentialUserEntity(
                    responseData.publicKeyCredentialCreationOptions.user.id.toByteArray(),//"jihyeon111".toByteArray(), //name
                    "null", //icon
                    responseData.publicKeyCredentialCreationOptions.user.name, //id
                    responseData.publicKeyCredentialCreationOptions.user.displayName //displayName
                )
            )
            .setChallenge(challenge)
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
        val fido2PendingIntentTask =
            fido2ApiClient.getRegisterIntent(options) // getRegisterPendingIntent

        fido2PendingIntentTask.addOnSuccessListener { fido2PendingIntent ->
            if (fido2PendingIntent.hasPendingIntent()) {
                try {
                    Log.e("실행돼라", "launching Fido2 Pending Intent")
                    fido2PendingIntent.launchPendingIntent(
                        this@ResistActivity,
                        REQUEST_CODE_REGISTER
                    )
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

        val registerFidoResult =
            "An Error Ocurred\n\nError Name:\n$errorName\n\nError Message:\n$errorMessage"

        //원래 코드 : resultText.text = registerFidoResult

        resultText.text = registerFidoResult

    }

    /**
     * The response should be sent to the Relying Party / server to validate and store
     */
    private fun handleRegisterResponse(fido2Response: ByteArray) {
        val response = AuthenticatorAttestationResponse.deserializeFromBytes(fido2Response)
        Log.e(LOG_TAG, "응답바디: $response")
        val keyHandleBase64 = Base64.encodeToString(response.keyHandle, Base64.URL_SAFE)

        val clientDataJson = String(response.clientDataJSON, Charsets.UTF_8)
        val clientDataJsonBase64 = Base64.encodeToString(response.clientDataJSON, Base64.URL_SAFE)
        clientDataJsonBase64.replace("\n", "")
        clientDataJsonBase64.replace("\\", "")
        val attestationObjectBase64 =
            Base64.encodeToString(response.attestationObject, Base64.URL_SAFE)
        attestationObjectBase64.replace("\n", "")
//        attestationObjectBase64.replace("\","")

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

        val SecondJsonData = JSONObject()

        SecondJsonData.put("userId", responseData.publicKeyCredentialCreationOptions.user.name)
        SecondJsonData.put("deviceId", "defaultDevice")
        SecondJsonData.put("transactionId", "null")

        val pubKeyCre = JSONObject()
        pubKeyCre.put("id", keyHandleBase64)
        pubKeyCre.put("type", "public-key")
        pubKeyCre.put("rawId", keyHandleBase64)
        SecondJsonData.put("publicKeyCredential", pubKeyCre)

        val respon = JSONObject()
        respon.put("clientDataJSON", clientDataJsonBase64)
        respon.put("attestationObject", attestationObjectBase64)
        SecondJsonData.put("response", respon)

        println(SecondJsonData)

        val body = JsonParser.parseString(SecondJsonData.toString()) as JsonObject

        ConnectServiceImpl.service.postResult(body).enqueue(object : Callback<SignUpData> {
            override fun onFailure(call: Call<SignUpData>, t: Throwable) {
                //통신 실패
                Log.e("result 실패", "실패실패실패")
            }

            override fun onResponse(call: Call<SignUpData>, response: Response<SignUpData>) {
                //통신 성공
                Log.e("result 성공", response.body().toString())
            }

        })

        //회원가입 성공-소켓 통신 호출
        //SigninConnect(nick).start()

        resultText.text = registerFido2Result
        Toast.makeText(this, "회원가입 성공했습니다", Toast.LENGTH_LONG).show()
        val intent = Intent(this, TutorialActivity::class.java);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent)
        finish()
    }

    /**
     * The response should be sent to the Relying Party / server to validate
     */
    fun handleSignResponse(fido2Response: ByteArray) {
        val response = AuthenticatorAssertionResponse.deserializeFromBytes(fido2Response)
        val keyHandleBase64 = Base64.encodeToString(response.keyHandle, Base64.DEFAULT)
        val clientDataJson = String(response.clientDataJSON, Charsets.UTF_8)
        val authenticatorDataBase64 =
            Base64.encodeToString(response.authenticatorData, Base64.DEFAULT)
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
        editor.putString(KEY_HANDLE_PREF, Base64.encodeToString(keyHandle, Base64.DEFAULT))
        editor.commit()
    }

//    class SigninConnect(nick: String) : Thread() {
//        private val usernick = nick
//        override fun run() {
//            try {
//                socket = Socket(ip, port)
//                writeSocket = DataOutputStream(socket.getOutputStream())
//                readSocket = DataInputStream(socket.getInputStream())
//
//                msg = "${DataManage.macAddress}/setinfo/${usernick}"
//                writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송
//                socket.close()
//            } catch (e: Exception) {    //연결 실패
//                socket.close()
//            }
//
//        }
//    }
}

private fun loadKeyHandle(): ByteArray? {
    val keyHandleBase64 = DataManage.key_handle
        ?: return null
    return Base64.decode(keyHandleBase64, Base64.DEFAULT)
}

