package kau.msproject.searchaed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kau.msproject.searchaed.ui.emergency.FirstFragment
import kau.msproject.searchaed.ui.emergency.SecondFragment
import kau.msproject.searchaed.ui.emergency.ThirdFragment

class EmergencyActivity : AppCompatActivity() {

    val aedInfo by lazy {intent.extras!!["AED"] as AedInfo}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment, FirstFragment.newInstance()).commit()

        var frCount = 0
        val btnNext : Button = findViewById(R.id.btn_next)
        btnNext.setOnClickListener(){
            if(frCount == 0){
                replaceFragment(SecondFragment.newInstance())
                frCount++
            }else if(frCount == 1){
                replaceFragment(ThirdFragment.newInstance())
                frCount++
            }else if(frCount == 2){
                replaceFragment(FirstFragment.newInstance())
                frCount = 0
            }
        }
    }

    public fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.fragment, fragment).commit()
    }
}
