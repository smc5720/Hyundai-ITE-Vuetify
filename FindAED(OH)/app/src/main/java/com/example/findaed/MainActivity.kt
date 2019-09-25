package com.example.findaed

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapSdk
import android.util.Log
import android.widget.Toast
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource


class MainActivity : AppCompatActivity(), OnMapReadyCallback  {

    private lateinit var locationSource: FusedLocationSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //맵 API연동, 클릭시 마커 남기는 기능까지
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("hz815ddbrf") // 기찬이 이거 쓸려면 clientid 발급 받아야함
        val fm = supportFragmentManager

        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)
        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        //여기까지 민철이

        //여기부터 버튼 코딩 - 기찬
        val create=findViewById<Button>(R.id.create)
        val remove : Button = findViewById(R.id.remove)
        create.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v : View?){
                createNotification()
            }
        })
        remove.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View?){
                removeNotification()
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(naverMap: NaverMap) {

        val uiSettings = naverMap.uiSettings
        uiSettings.isLocationButtonEnabled = true

        naverMap.locationSource = locationSource

        naverMap.setOnMapClickListener { point, coord ->
            Toast.makeText(this, "${coord.latitude}, ${coord.longitude}",
                Toast.LENGTH_SHORT).show()

            val marker = Marker()
            marker.position = LatLng(coord.latitude.toDouble(), coord.longitude.toDouble())
            marker.map = naverMap
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    fun createNotification(){
        //object builder<NotificationCompat.Builder>=NotificationCompat.Builder(this,"default")
        val builder : NotificationCompat.Builder = NotificationCompat.Builder(this,"default")
        builder.setSmallIcon(R.mipmap.ic_launcher) //아이콘
        builder.setContentTitle("현재 위치로부터 약 100m지점에서 심정지 환자 발생") //세부제목
        builder.setContentText("항공대학로 76 앞 지원 가능 할 경우 지원 바람") //세부내용
        builder.setColor(Color.RED) //알림 색깔?
        builder.setAutoCancel(true)     //사용자가 탭을 클릭하면 자동 닫기
        //알림생성
        val notificationManager : NotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            notificationManager.createNotificationChannel(NotificationChannel("default","기본채널",NotificationManager.IMPORTANCE_DEFAULT))
        }
        // id값은
        // 정의해야하는 각 알림의 고유한 int값
        notificationManager.notify(1, builder.build())
    }
    fun removeNotification(){
        NotificationManagerCompat.from(this).cancel(1);
    }

}