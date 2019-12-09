package kau.msproject.searchaed

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kau.msproject.searchaed.ui.emergency.FirstFragment
import kau.msproject.searchaed.ui.emergency.FourthFragment
import kau.msproject.searchaed.ui.emergency.SecondFragment
import kau.msproject.searchaed.ui.emergency.ThirdFragment

class EmergencyActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency2)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.emergency_fragment, FirstFragment.newInstance()).commit()
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS),
            PackageManager.PERMISSION_GRANTED
        )
        var frCount = 0
        val btnNext : Button = findViewById(R.id.btn_nextf)
        btnNext.setOnClickListener(){ view ->
            if(frCount == 0){
                replaceFragment(SecondFragment.newInstance())
                frCount++
            }else if(frCount == 1){
                replaceFragment(ThirdFragment.newInstance())
                //문자발송
                    val phoneNo: String = "123456789" //119번호
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
                frCount++
            }else if(frCount == 2){
                replaceFragment(FourthFragment.newInstance())
                FcmPush.instance.send()
                frCount++
            }
            else if(frCount == 3){
                //replaceFragment(FirstFragment.newInstance())
                //frCount = 0
            }
        }
        val btnClose : Button = findViewById(R.id.btn_close)
        btnClose.setOnClickListener(){
            intent.putExtra("result", 1)
            setResult(Activity.RESULT_OK,intent)

            finish()
        }
    }

    fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.emergency_fragment, fragment).commit()
    }

}
