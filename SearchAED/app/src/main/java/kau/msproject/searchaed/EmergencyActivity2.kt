package kau.msproject.searchaed

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
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

        var frCount = 0
        val btnNext : Button = findViewById(R.id.btn_nextf)
        btnNext.setOnClickListener(){
            if(frCount == 0){
                replaceFragment(SecondFragment.newInstance())
                frCount++
            }else if(frCount == 1){
                replaceFragment(ThirdFragment.newInstance())
                frCount++
            }else if(frCount == 2){
                replaceFragment(FourthFragment.newInstance())
                frCount++
            }
            else if(frCount == 3){
                replaceFragment(FirstFragment.newInstance())
                frCount = 0
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
