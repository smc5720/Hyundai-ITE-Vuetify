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
//알림 보내기
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
    //알림 클라이언트->클라이언트로 보내는 함수
    fun send() {
        var mylat: Double = 0.0
        var mylon: Double = 0.0
        var arr = ArrayList<String>()
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
                //데이터베이스에서 자기 위치 정보를 가져옴
                val database = FirebaseDatabase.getInstance()
                val mRef = database.getReference("user")
                //데이터베이스에서 자기위도값 근처에 있는 사람들의 토큰값을 가져와서 arraylist에 저장함
                mRef.orderByChild("lat").startAt(mylat - 1).endAt(mylat+1)
                    .addChildEventListener(object : ChildEventListener {
                        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                            if(p1!=null) arr.add(p1)
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
                //데이터베이스에서 자기경도값 근처에 있는 사람들의 토큰값을 가져 온 후 위도값이 저장되어있는 arraylist와 비교하여 일치하면 알림 푸시
                mRef.orderByChild("lon").startAt(mylon-1).endAt(mylon+1).addChildEventListener(object : ChildEventListener{
                    override fun onChildAdded(datasnap: DataSnapshot, Push_token: String?) {

                        for(i in 0 until arr.size-1){
                            if(arr.get(i)==Push_token){
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

                        }




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