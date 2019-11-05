package kau.msproject.searchaed.ui.home

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.*

import kau.msproject.searchaed.R
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import kau.msproject.searchaed.AedInfo
import kau.msproject.searchaed.AedInfoActivity
import kau.msproject.searchaed.MainActivity

class HomeFragment : Fragment(), OnMapReadyCallback {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var locationSource: FusedLocationSource

    val dataOfAED = arrayOfNulls<Map<String, Object>>(100)
    val infoOfAED = arrayOfNulls<AedInfo>(100) // intent로 넘겨주기 위해 설정

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
        val root = inflater.inflate(R.layout.home_fragment, container, false)

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

                for (i in 0 until 100) {
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

                Log.d("DataBase", "Value is: ${dataOfAED[1]!!.get("buildAddress")}")
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
        locationSource =
            FusedLocationSource(this,
                LOCATION_PERMISSION_REQUEST_CODE
            )
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

        val uiSettings = naverMap.uiSettings
        val marker = arrayOfNulls<Marker>(100)

        for (i in 0 until 100) {
            marker[i] = Marker()
        }

        // 정보창의 내용을 지정한다.
        val infoWindow = InfoWindow()
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

            var idx: Int = infoWindow.marker?.tag as Int ?: 0
            val aedIntent = Intent(activity, AedInfoActivity::class.java)
            aedIntent.putExtra("AED", infoOfAED[idx])
            //클릭해야 idx가 생성이기 때문에 null발생 불가
            startActivityForResult(aedIntent,1)

            true
        }

        // 현 위치 버튼 활성화
        uiSettings.isLocationButtonEnabled = true

        // '현 위치 관련 기능 (FusedLocationSource)' 사용
        naverMap.locationSource = locationSource

        // 맵을 클릭했을 때 불린다.
        naverMap.setOnMapClickListener { point, coord ->
            /*
            Toast.makeText(
                this, "${coord.latitude}, ${coord.longitude}",
                Toast.LENGTH_SHORT
            ).show()
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

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }


}
