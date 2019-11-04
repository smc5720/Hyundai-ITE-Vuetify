package kau.msproject.searchaed

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import android.view.Menu
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.ui.AppBarConfiguration
import kau.msproject.searchaed.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.util.FusedLocationSource
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val REQUEST_CALL: Int = 1
    init {
        instance = this
    }

    companion object {
        private var instance: MainActivity? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
    //val dataOfAED = arrayOfNulls<Map<String, Object>>(100)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS),
            PackageManager.PERMISSION_GRANTED
        )
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("hz815ddbrf")

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        //문자버튼
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            val phoneNo: String = "010-1234-5678" //119번호
            val sms: String = "현재 ___위치에 긴급 환자가 발생 했습니다. 도움 요청 부탁드립니다!!"   //문자내용
            try {
                var smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(phoneNo, null, sms, null, null)
                val context: Context = MainActivity.applicationContext()
                Toast.makeText(context, "전송 완료!", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                val context: Context = MainActivity.applicationContext()
                Toast.makeText(context, "SMS faild, please try again later!", Toast.LENGTH_LONG)
                    .show()
                e.printStackTrace()
            }
        }
        //전화버튼
        val buttonCall = findViewById<Button>(R.id.buttonCall)
        buttonCall.setOnClickListener {
            val itentCall : Intent = Intent(Intent.ACTION_CALL)
            val phoneNo: String = "010-1234-5678" //건물번호
            Toast.makeText(this,"Please Enter Your Number123",Toast.LENGTH_SHORT)
            if(phoneNo.trim().isEmpty()){
                Toast.makeText(this,"Please Enter Your Number",Toast.LENGTH_SHORT)
            }else{
                itentCall.setData(Uri.parse("tel:" + phoneNo))
            }
            if(ActivityCompat.checkSelfPermission(applicationContext,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Please Grant Permission",Toast.LENGTH_SHORT)
                requestionPermission()
            }else{
                startActivity(itentCall)
            }
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.aed_home, R.id.aed_youtube
            ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }
    //전화
    fun requestionPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),REQUEST_CALL)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
