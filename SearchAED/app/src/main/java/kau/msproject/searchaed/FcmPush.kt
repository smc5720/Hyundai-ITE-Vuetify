package kau.msproject.searchaed

import android.app.DownloadManager
import android.content.ContentValues.TAG
import android.os.Message
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import kau.msproject.searchaed.ui.PushDTO
import okhttp3.*
import java.io.IOException

class FcmPush() {
    var JSON = MediaType.parse("application/json; charset=utf-8")
    var url = "https://fcm.googleapis.com/fcm/send"
    var serverKey = "AIzaSyDiQMKmWLGIYvJKr0yi5s16j1MQqsKO0D8"
    var gson : Gson? =null
    var okHttpClient : OkHttpClient? =null
    var database = FirebaseDatabase.getInstance().getReference();
    var ref = database.child("profiles");

    companion object{
        var instance = FcmPush()
    }
    init{
        gson = Gson()
        okHttpClient = OkHttpClient()
    }
    fun send(){
        var token = "edSjoqfDNXk:APA91bGvmCsAqDHN71V7m6NSHdbHXxyJsItU2Do-w7F64Ju7Nk46T8FZiHiH2VDvcIJSwRh6tK_ull1JOvdj0HN3A2M-IaVRSLdopnarQWssEm5YH6L5BMy7sbBRp00ouwz_liDJbgWg"
        var pushDTO= PushDTO()
        pushDTO.to = token
        pushDTO.notification.title ="위급상황입니다"
        pushDTO.notification.body = "_____로 AED를 가지고와 주세요"
        var body =okhttp3.RequestBody.create(JSON,gson?.toJson(pushDTO))
        var request = okhttp3.Request.Builder()
            .addHeader("Content-Type","application/json")
            .addHeader("Authorization","key=AIzaSyDiQMKmWLGIYvJKr0yi5s16j1MQqsKO0D8")
            .url(url)
            .post(body)
            .build()
        okHttpClient?.newCall(request)?.enqueue(object : Callback{

            override fun onFailure(call: Call, e: IOException) {

            }
            override fun onResponse(call: Call, response: Response) {
                println(response?.body()?.string())
            }
        })


    }
}