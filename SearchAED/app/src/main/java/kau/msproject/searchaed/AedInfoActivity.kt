package kau.msproject.searchaed

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.widget.TextView

class AedInfoActivity : Activity() {

    val aedInfo by lazy {intent.extras!!["AED"] as AedInfo}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_aed_info)

        var textBuildAddress : TextView = findViewById(R.id.textBuildAddress)
        var textZipCode1 : TextView = findViewById(R.id.textZipcode1)
        var textZipCode2 : TextView = findViewById(R.id.textZipcode2)
        var textOrg : TextView = findViewById(R.id.textOrg)
        var textClerkTel : TextView = findViewById(R.id.textClerkTel)
        var textBuildPlace : TextView = findViewById(R.id.textBuildPlace)
        var textManager : TextView = findViewById(R.id.textManager)
        var textManagerTel : TextView = findViewById(R.id.textManagerTel)
        var textModel : TextView = findViewById(R.id.textModel)

        textBuildAddress.text = aedInfo.buildAddress
        textZipCode1.text = aedInfo.zipCode1
        textZipCode2.text = aedInfo.zipCode2
        textOrg.text = aedInfo.org
        textClerkTel.text = aedInfo.clerkTel
        textBuildPlace.text = aedInfo.buildPlace
        textManager.text = aedInfo.manager
        textManagerTel.text = aedInfo.managerTel
        textModel.text = aedInfo.model
    }

    fun mOnClose(view : View){
        intent.putExtra("result", aedInfo)
        setResult(RESULT_OK,intent)

        finish()
    }

    fun mOnCall(){

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
