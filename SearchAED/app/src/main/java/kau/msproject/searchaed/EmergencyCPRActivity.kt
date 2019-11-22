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
import kau.msproject.searchaed.ui.emergencyCPR.*

class EmergencyCPRActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_cpr)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.emergency_cpr_fragment, CprFirstFragment.newInstance()).commit()

        var frCount = 0
        val btnNext : Button = findViewById(R.id.btn_nextcpr)
        btnNext.setOnClickListener(){
            if(frCount == 0){
                replaceFragment(CprSecondFragment.newInstance())
                frCount++
            }else if(frCount == 1){
                replaceFragment(CprThirdFragment.newInstance())
                frCount++
            }else if(frCount == 2){
                replaceFragment(CprFourthFragment.newInstance())
                frCount++
            }
            else if(frCount == 3){
                replaceFragment(CprFifthFragment.newInstance())
                frCount = 4
            }
            else if(frCount == 4){
                replaceFragment(CprSixthFragment.newInstance())
                frCount = 5
            }
            else if(frCount == 5){
                //replaceFragment(CprFirstFragment.newInstance())
                //frCount = 0
            }
        }
    }

    fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.emergency_cpr_fragment, fragment).commit()
    }
}
