package kau.msproject.searchaed

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import kau.msproject.searchaed.ui.emergency.FirstFragment
import kau.msproject.searchaed.ui.emergency.FourthFragment
import kau.msproject.searchaed.ui.emergency.SecondFragment
import kau.msproject.searchaed.ui.emergency.ThirdFragment
import kau.msproject.searchaed.ui.emergencyAED.*

class EmergencyAEDActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_aed)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.emergency_aed_fragment, AedFirstFragment.newInstance()).commit()

        var frCount = 0
        val btnNext : Button = findViewById(R.id.btn_nextaed)
        btnNext.setOnClickListener(){
            if(frCount == 0){
                replaceFragment(AedSecondFragment.newInstance())
                frCount++
            }else if(frCount == 1){
                replaceFragment(AedThirdFragment.newInstance())
                frCount++
            }else if(frCount == 2){
                replaceFragment(AedFourthFragment.newInstance())
                frCount++
            }
            else if(frCount == 3){
                replaceFragment(AedFifthFragment.newInstance())
                frCount = 4
            }
            else if(frCount == 4){
                replaceFragment(AedSixthFragment.newInstance())
                frCount = 5
            }
            else if(frCount == 5){
                //replaceFragment(AedFirstFragment.newInstance())
                //frCount = 0
            }
        }
    }

    fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.emergency_aed_fragment, fragment).commit()
    }

}

