package kau.smc.findaed

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


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var locationSource: FusedLocationSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Realtime-Database에 message 태그 생성 후 "Hello, World!" 추가
         var database: FirebaseDatabase = FirebaseDatabase.getInstance()
         var databaseReference: DatabaseReference = database.getReference("records")
        // databaseReference.setValue("Hello, World!")

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
        val marker = Marker()

        // 정보창의 내용을 지정한다.
        val infoWindow = InfoWindow()
        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return "정보 창 내용"
            }
        }

        marker.position = LatLng(37.5921280, 126.97942)
        marker.map = naverMap

        // 마커의 위치에 정보창을 띄운다.
        infoWindow.open(marker)

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

            // 맵을 클릭하면 정보창을 닫는다.
            infoWindow.close()
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

        marker.onClickListener = listener
    }

    // '현 위치 관련 기능 (FusedLocationSource)' 사용
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}