package kau.msproject.searchaed.ui.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import kau.msproject.searchaed.R
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource

class HomeFragment : Fragment(), OnMapReadyCallback {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var locationSource: FusedLocationSource
    var checkState: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

        //callMapAPI()

        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.home_fragment, container, false)

        return root
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
        uiSettings.isLocationButtonEnabled = true

        naverMap.locationSource = locationSource

        naverMap.setOnMapClickListener { point, coord ->
            Toast.makeText(activity, "${coord.latitude}, ${coord.longitude}",
                Toast.LENGTH_SHORT).show()

            val marker = Marker()
            marker.position = LatLng(coord.latitude.toDouble(), coord.longitude.toDouble())
            marker.map = naverMap
        }
        /*val uiSettings = naverMap.uiSettings
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
                activity, "${coord.latitude}, ${coord.longitude}",
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
        }*/
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }


}
