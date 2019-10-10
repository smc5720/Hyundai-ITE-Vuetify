package com.example.munga

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.text.trimmedLength
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val buttonCall = findViewById<Button>(R.id.buttonCall)
        val buttonSend = findViewById<Button>(R.id.buttonSend)
        val textPhoneNo = findViewById<EditText>(R.id.editTextPhoneNo)
        val  textSMS = findViewById<EditText>(R.id.editTextSMS)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS),
            PackageManager.PERMISSION_GRANTED
        )
        //통화
        buttonCall.setOnClickListener {
            val itentCall : Intent  = Intent(Intent.ACTION_CALL)
            val phoneNo: String = textPhoneNo.text.toString()
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
        //버튼 클릭이벤트
        buttonSend.setOnClickListener {

            val phoneNo: String = textPhoneNo.text.toString()
            val sms: String = textSMS.text.toString()
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

    }

     fun requestionPermission(){
      ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),REQUEST_CALL)
  }
}
