package kau.smc.findaed

import android.content.Context
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import org.w3c.dom.Comment
import java.util.*


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var locationSource: FusedLocationSource

    val dataOfAED = arrayOfNulls<Map<String, Object>>(100)

    // false: 마커 생성 전
    var checkState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setRealtimeDatabase()

        callMapAPI()
    }

    fun setRealtimeDatabase() {
        // Realtime-Database에 message 태그 생성 후 "Hello, World!" 추가
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference: DatabaseReference = database.getReference("records")
        // databaseReference.setValue("Hello, World!")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue() as ArrayList<String>

                for (i in 0 until 100) {
                    dataOfAED[i] = value.get(i) as Map<String, Object>
                }

                Log.d("DataBase", "Value is: ${dataOfAED[1]!!.get("buildAddress")}")
                // 정보를 위의 반복문에서 넣기 때문에 Null로 연산될 일이 없다.
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ERROR", "Failed to read value.", error.toException())
            }
        })
    }

    fun callMapAPI() {
        // 맵 API 호출
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("fbjwqr85b8")

        // API 호출을 위한 NaverMap 객체 등록
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)

        // '현 위치 관련 기능 (FusedLocationSource)' 사용
        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    // '현 위치 관련 기능 (FusedLocationSource)' 사용
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions,
                grantResults
            )
        ) {
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(naverMap: NaverMap) {

        val uiSettings = naverMap.uiSettings
        val marker = arrayOfNulls<Marker>(100)

        for (i in 0 until 100) {
            marker[i] = Marker()
        }

        // 정보창의 내용을 지정한다.
        val infoWindow = InfoWindow()
        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                var idx: Int = infoWindow.marker?.tag as Int
                return "주소: ${dataOfAED[idx]!!.get("buildAddress")}\n" +
                        "우편 번호: ${dataOfAED[idx]!!.get("zipcode1")}-${dataOfAED[idx]!!.get("zipcode2")}\n" +
                        "건물 이름: ${dataOfAED[idx]!!.get("org")}\n" +
                        "건물 번호: ${dataOfAED[idx]!!.get("clerkTel")}\n" +
                        "상세 위치: ${dataOfAED[idx]!!.get("buildPlace")}\n" +
                        "담당자: ${dataOfAED[idx]!!.get("manager")}\n" +
                        "담당자 번호: ${dataOfAED[idx]!!.get("managerTel")}\n" +
                        "AED 모델: ${dataOfAED[idx]!!.get("model")}"
            }
        }

        // 마커 클릭 시 호출되는 리스너
        val listener = Overlay.OnClickListener { overlay ->
            val marker = overlay as Marker

            if (marker.infoWindow == null) {
                // 현재 마커에 정보 창이 열려있지 않을 경우 엶
                infoWindow.open(marker)
            } else {
                // 이미 현재 마커에 정보 창이 열려있을 경우 닫음
                infoWindow.close()
            }

            true
        }

        // 마커의 위치에 정보창을 띄운다.
        //infoWindow.open(marker)

        // 현 위치 버튼 활성화
        uiSettings.isLocationButtonEnabled = true

        // '현 위치 관련 기능 (FusedLocationSource)' 사용
        naverMap.locationSource = locationSource

        // 맵을 클릭했을 때 불린다.
        naverMap.setOnMapClickListener { point, coord ->
            Toast.makeText(
                this, "${coord.latitude}, ${coord.longitude}",
                Toast.LENGTH_SHORT
            ).show()

            /*
            marker.position = LatLng(coord.latitude.toDouble(), coord.longitude.toDouble())
            marker.map = naverMap
            */
            if (checkState == false) {
                for (i in 0 until 100) {
                    if (dataOfAED[i] != null) {
                        val wgs84Lat: Double = (dataOfAED[i]!!.get("wgs84Lat") as String).toDouble()
                        val wgs84Lon: Double = (dataOfAED[i]!!.get("wgs84Lon") as String).toDouble()

                        marker[i]!!.position = LatLng(wgs84Lat, wgs84Lon)
                        marker[i]!!.map = naverMap
                        marker[i]!!.tag = i
                        marker[i]!!.onClickListener = listener
                    }
                }

                checkState = true
            }

            // 맵을 클릭하면 정보창을 닫는다.
            infoWindow.close()
        }
    }

    // '현 위치 관련 기능 (FusedLocationSource)' 사용
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}