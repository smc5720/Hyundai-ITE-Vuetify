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
    var gson: Gson? = null
    var okHttpClient: OkHttpClient? = null
    var database = FirebaseDatabase.getInstance().getReference();
    var ref = database.child("profiles");
    val tokenID: String? = FirebaseInstanceId.getInstance().getToken()

    companion object {
        var instance = FcmPush()
    }

    init {
        gson = Gson()
        okHttpClient = OkHttpClient()
    }

    fun send() {
        var mylat: Double = 0.0
        var mylon: Double = 0.0

     //   storeDatabase(tokenID, 37.0, 126.0)
        var database1: FirebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference: DatabaseReference = database1.getReference("user")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.value as Map<String, Object>
                val tokenData = value.get(tokenID.toString()) as Map<String, Object>

                var my_lat = tokenData.get("lat")
                var my_lon = tokenData.get("lon")
                mylat = my_lat.toString().toDouble()
                mylon = my_lon.toString().toDouble()
                println("지나감 : mylat : ${mylat} , ${my_lat}")
                println("지나감 : ${mylat} ")
                val database = FirebaseDatabase.getInstance()
                val mRef = database.getReference("user")
                mRef.orderByChild("lat").startAt(mylat - 1).endAt(mylat+1)
                    .addChildEventListener(object : ChildEventListener {
                        override fun onChildAdded(p0: DataSnapshot, p1: String?) {

                             mRef.orderByChild("lon").startAt(mylon-1).endAt(mylon+1).addChildEventListener(object : ChildEventListener{
                                override fun onChildAdded(datasnap: DataSnapshot, Push_token: String?) {

                                        var token = Push_token
                                        var pushDTO = PushDTO()
                                        pushDTO.to = token
                                        pushDTO.notification.title = "위급상황입니다"
                                        pushDTO.notification.body =
                                            "위도 : ${mylat}, 경도 : ${mylon}로 AED를 가지고와 주세요"
                                        var body =
                                            okhttp3.RequestBody.create(JSON, gson?.toJson(pushDTO))
                                        var request = okhttp3.Request.Builder()
                                            .addHeader("Content-Type", "application/json")
                                            .addHeader(
                                                "Authorization",
                                                "key=AIzaSyDiQMKmWLGIYvJKr0yi5s16j1MQqsKO0D8"
                                            )
                                            .url(url)
                                            .post(body)
                                            .build()
                                        okHttpClient?.newCall(request)?.enqueue(object : Callback {
                                            override fun onFailure(call: Call, e: IOException) {

                                            }

                                            override fun onResponse(
                                                call: Call,
                                                response: Response
                                            ) {
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

            override fun onCancelled(error: DatabaseError) {
                Log.w("ERROR", "Failed to read value.", error.toException())
            }
        })


    }

    fun storeDatabase(tokenID: String?, latitude: Double, longitude: Double) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("user")
        if (tokenID != null) {
            //myRef.child(tokenID).setValue("${location.latitude}, ${location.longitude}")
            myRef.child(tokenID).child("lat").setValue(latitude)
            myRef.child(tokenID).child("lon").setValue(longitude)
        }
    }
}