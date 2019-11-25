package kau.msproject.searchaed

import android.app.DownloadManager
import android.content.ContentValues.TAG
import android.os.Message
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import kau.msproject.searchaed.ui.PushDTO
import kau.msproject.searchaed.ui.home.HomeFragment
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
    public var mylat :Double = 0.0
    public var mylon :Double = 0.0
    companion object{
        var instance = FcmPush()
    }
    init{
        gson = Gson()
        okHttpClient = OkHttpClient()
    }

    fun send(){
        HomeFragment().getLocationToken(mylat,mylon)
        mylat -=1
        mylon +=1
        val database = FirebaseDatabase.getInstance()
        val mRef = database.getReference("user")
        mRef.orderByChild("lat").startAt(mylat).endAt(mylat)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    Toast.makeText(
                        MainActivity.applicationContext(),
                        p1,
                        Toast.LENGTH_SHORT
                    ).show()
                    println("토큰값 : "+p1)
                    var token = p1
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
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })



    }
}