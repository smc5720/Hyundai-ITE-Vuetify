package kau.msproject.searchaed.ui.home

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId

import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.*
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import kau.msproject.searchaed.*
import kau.msproject.searchaed.R

class HomeFragment : Fragment(), OnMapReadyCallback {

    val dataNum:Int = 100;

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var locationSource: FusedLocationSource
    private lateinit var root : View
    val dataOfAED = arrayOfNulls<Map<String, Object>>(dataNum)
    val infoOfAED = arrayOfNulls<AedInfo>(dataNum) // intent로 넘겨주기 위해 설정

    // 카메라 무빙
    var cameraLat:Double = 37.5666102
    var cameraLon:Double = 126.9783881

    var checkState: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setRealtimeDatabase()

        callMapAPI()

        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        root = inflater.inflate(R.layout.home_fragment, container, false)
        //응급상황발생 버튼

        val emergencyButton2 = root.findViewById<Button>(R.id.btn_emergency2)
        emergencyButton2.setOnClickListener(){
            val emergencyIntent = Intent(activity,EmergencyActivity2::class.java)
            emergencyIntent.putExtra("AED", infoOfAED[1])
            startActivity(emergencyIntent)
        }

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        return root
    }

    fun setRealtimeDatabase() {
        // Realtime-Database에 message 태그 생성 후 "Hello, World!" 추가
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference: DatabaseReference = database.getReference("records")
        // databaseReference.setValue("Hello, World!")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.value as ArrayList<String>

                for (i in 0 until dataNum) {
                    dataOfAED[i] = value.get(i) as Map<String, Object>
                    infoOfAED[i] = AedInfo(dataOfAED[i]!!.get("buildAddress").toString(),
                        dataOfAED[i]!!.get("zipcode1").toString(),
                        dataOfAED[i]!!.get("zipcode2").toString(),
                        dataOfAED[i]!!.get("org").toString(),
                        dataOfAED[i]!!.get("clerkTel").toString(),
                        dataOfAED[i]!!.get("buildPlace").toString(),
                        dataOfAED[i]!!.get("manager").toString(),
                        dataOfAED[i]!!.get("managerTel").toString(),
                        dataOfAED[i]!!.get("model").toString()
                        )
                }
                // 정보를 위의 반복문에서 넣기 때문에 Null로 연산될 일이 없다.
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ERROR", "Failed to read value.", error.toException())
            }
        })
    }

    fun callMapAPI() {
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)
        //여기까지 민철이
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions,
                grantResults)) {
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(naverMap: NaverMap) {

        // 최소 및 최대 줌 레벨 설정
        naverMap.minZoom = 5.0
        naverMap.maxZoom = 18.0

        // 카메라의 대상 지점을 한반도 인근으로 제한
        naverMap.extent = LatLngBounds(LatLng(31.43, 122.37), LatLng(44.35, 132.0))

        // 실시간 교통정보
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRAFFIC, true)

        // uiSetting: 현 위치 버튼 활성화
        val uiSettings = naverMap.uiSettings
        val marker = arrayOfNulls<Marker>(dataNum)

        for (i in 0 until dataNum) {
            marker[i] = Marker()
        }

        // 정보창의 내용을 지정한다.
        /*val infoWindow = InfoWindow()
        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(MainActivity.applicationContext()) {
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
        }*/

        // 마커 클릭 시 호출되는 리스너
        val listener = Overlay.OnClickListener { overlay ->
            val marker = overlay as Marker

            cameraLat = marker.position.latitude
            cameraLon = marker.position.longitude

            // 카메라 무빙
            var cameraUpdate = CameraUpdate.scrollAndZoomTo(LatLng(cameraLat, cameraLon), 15.0).animate(CameraAnimation.Easing, 500)
            naverMap.moveCamera(cameraUpdate)

            /*if (marker.infoWindow == null) {
                // 현재 마커에 정보 창이 열려있지 않을 경우 엶
                infoWindow.open(marker)
            } else {
                // 이미 현재 마커에 정보 창이 열려있을 경우 닫음
                infoWindow.close()
            }*/

            var idx: Int = marker.tag as Int

            var textBuildAddress : TextView = root.findViewById(R.id.textBuildAddress)
            var textOrg : TextView = root.findViewById(R.id.textOrg)
            var textClerkTel : TextView = root.findViewById(R.id.textClerkTel)
            var textBuildPlace : TextView = root.findViewById(R.id.textBuildPlace)
            var textManagerTel : TextView = root.findViewById(R.id.textManagerTel)


            textBuildAddress.text = infoOfAED[idx]!!.buildAddress
            textOrg.text = infoOfAED[idx]!!.org
            textClerkTel.text = infoOfAED[idx]!!.clerkTel
            textBuildPlace.text = infoOfAED[idx]!!.buildPlace
            textManagerTel.text = infoOfAED[idx]!!.managerTel

            true
        }

        // 현 위치 버튼 활성화
        uiSettings.isLocationButtonEnabled = true
        uiSettings.isCompassEnabled = false

        // '현 위치 관련 기능 (FusedLocationSource)' 사용
        naverMap.locationSource = locationSource

        // 맵을 클릭했을 때 불린다.
        naverMap.setOnMapClickListener { point, coord ->

            if (checkState == false) {
                for (i in 0 until dataNum) {
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

            // 위치가 변경될 때마다 호출된다.
            naverMap.addOnLocationChangeListener { location ->
                Toast.makeText(MainActivity.applicationContext(), "${location.latitude}, ${location.longitude}",
                    Toast.LENGTH_SHORT).show()

                var tokenID : String?= FirebaseInstanceId.getInstance().getToken()

                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("user")
                if(tokenID!= null) {
                    myRef.child(tokenID).setValue("${location.latitude}, ${location.longitude}")
                }
            }

            // 맵을 클릭하면 정보창을 닫는다.
            //infoWindow.close()
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }


}
