package kau.msproject.searchaed.ui.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.database.*
import com.google.firebase.database.snapshot.ChildKey
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
    private val REQUEST_CALL: Int = 1
    /*init {
        instance = this
    }*/
/*
    fun applicationContext(): Context {
        return MainActivity.applicationContext()
    }*/
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var locationSource: FusedLocationSource
    private lateinit var root : View
    val dataOfAED = arrayOfNulls<Map<String, Object>>(dataNum)
    val infoOfAED = arrayOfNulls<AedInfo>(dataNum) // intent로 넘겨주기 위해 설정

    // 카메라 무빙
    var cameraLat:Double = 37.5666102
    var cameraLon:Double = 126.9783881

    var checkState: Boolean = false

    // 토큰 값
    val tokenID : String?= FirebaseInstanceId.getInstance().getToken()

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
           emergencyButton2.setOnClickListener(

           ){
            val emergencyIntent = Intent(activity,EmergencyActivity2::class.java)
            emergencyIntent.putExtra("AED", infoOfAED[1])
            startActivity(emergencyIntent)
        }

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        return root
    }

    fun setRealtimeDatabase() {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference: DatabaseReference = database.getReference("records")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.value as ArrayList<String>
                //Toast.makeText(MainActivity.applicationContext(), dataSnapshot.value.toString(), Toast.LENGTH_LONG).show()

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

    // Firebase에 저장된 Token: 위도, 경도 값을 읽어온다.
    // fb는 firebase의 약자이며, fb_lat과 fb_lon에는 각각 firebase에서 읽어온 위도와 경도 값이 저장된다.
    // tokenData 변수를 선언할 때, value.get 부분의 tokenID에 찾고자 하는 클라이언트의 token 값을 넣어주면 된다.


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
            //매니저에게 전화
            val mgr_call_btn =root.findViewById<Button>(R.id.btn_call_manager)
            mgr_call_btn.setOnClickListener(){
                val itentCall : Intent = Intent(Intent.ACTION_CALL)
                val phoneNo: String = textManagerTel.text.toString() //건물번호
                Toast.makeText(MainActivity.applicationContext(),"Please Enter Your Number123",Toast.LENGTH_SHORT)
                if(phoneNo.trim().isEmpty()){
                    Toast.makeText(MainActivity.applicationContext(),"Please Enter Your Number",Toast.LENGTH_SHORT)
                }else{
                    itentCall.setData(Uri.parse("tel:" + phoneNo))
                }
                if(ActivityCompat.checkSelfPermission(MainActivity.applicationContext(),Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.applicationContext(),"Please Grant Permission",Toast.LENGTH_SHORT)
                    requestionPermission()

                }else{
                    startActivity(itentCall)
                }
            }
            //건물관리자에게 전화
            val place_call_btn = root.findViewById<Button>(R.id.btn_call_place)
            place_call_btn.setOnClickListener(){
                val itentCall : Intent = Intent(Intent.ACTION_CALL)
                val phoneNo: String = textClerkTel.text.toString() //건물번호
                Toast.makeText(MainActivity.applicationContext(),"Please Enter Your Number123",Toast.LENGTH_SHORT)
                if(phoneNo.trim().isEmpty()){
                    Toast.makeText(MainActivity.applicationContext(),"Please Enter Your Number",Toast.LENGTH_SHORT)
                }else{
                    itentCall.setData(Uri.parse("tel:" + phoneNo))
                }
                if(ActivityCompat.checkSelfPermission(MainActivity.applicationContext(),Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.applicationContext(),"Please Grant Permission",Toast.LENGTH_SHORT)
                    requestionPermission()

                }else{
                    startActivity(itentCall)
                }
            }
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
                //위치가 변경될 때마다 데이터베이스에 위도 경도 실시간 저장
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("user")
                if (tokenID != null) {
                    //myRef.child(tokenID).setValue("${location.latitude}, ${location.longitude}")
                    myRef.child(tokenID).child("lat").setValue(location.latitude)
                    myRef.child(tokenID).child("lon").setValue(location.longitude)
                }
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    fun requestionPermission() {
        // ActivityCompat.requestPermissions(MainActivity(), arrayOf(Manifest.permission.CALL_PHONE),REQUEST_CALL)
        if (ContextCompat.checkSelfPermission(
                MainActivity.applicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.CALL_PHONE),
                REQUEST_CALL
            )

        }
    }

}
