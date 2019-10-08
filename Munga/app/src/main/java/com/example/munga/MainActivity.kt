package com.example.munga

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {

    init {
        instance = this
    }

    companion object {
        private var instance: MainActivity? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //버튼 클릭이벤트

        var buttonSend = findViewById<Button>(R.id.buttonSend)
        var textPhoneNo = findViewById<EditText>(R.id.editTextPhoneNo)
        var textSMS = findViewById<EditText>(R.id.editTextSMS)
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS),PackageManager.PERMISSION_GRANTED)
        buttonSend.setOnClickListener {
            var phoneNo: String = textPhoneNo.text.toString()
            var sms: String = textSMS.text.toString()
           try {
                var smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(phoneNo, null, sms, null, null)
                val context: Context = MainActivity.applicationContext()
                Toast.makeText(context, "전송 완료!", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                val context: Context = MainActivity.applicationContext()
                Toast.makeText(context,"SMS faild, please try again later!", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }
}
