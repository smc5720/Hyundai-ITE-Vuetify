package com.example.aedinfo

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.widget.TextView

class AedInfoPopup : Activity() {

    val aed by lazy {intent.extras!!["Aed"] as Aed}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.aed_info)

        var textAedAddress : TextView = findViewById(R.id.aedAddress)
        var textAedNumber : TextView = findViewById(R.id.aedNumber)
        var textAedLong : TextView = findViewById(R.id.aedLong)

        textAedAddress.text = aed.address
        textAedNumber.text = aed.number
        textAedLong.text = aed.long

    }
    fun mOnClose(view: View){
        intent.putExtra("result", "Close Popup")
        setResult(RESULT_OK,intent)

        finish()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event?.action == MotionEvent.ACTION_OUTSIDE){
            return false
        }
        return true
    }

    override fun onBackPressed() {
        return
    }
}