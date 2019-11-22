package kau.msproject.searchaed.ui.emergency

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kau.msproject.searchaed.EmergencyAEDActivity
import kau.msproject.searchaed.EmergencyActivity2
import kau.msproject.searchaed.EmergencyCPRActivity

import kau.msproject.searchaed.R

class FourthFragment : Fragment() {

    companion object {
        fun newInstance() = FourthFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_fourth, container, false)

        val btnCpr : Button = root.findViewById(R.id.btn_cpr)
        btnCpr.setOnClickListener(){
            val cprIntent = Intent(activity, EmergencyCPRActivity::class.java)
            cprIntent.putExtra("What", 1)
            startActivity(cprIntent)
        }

        val btnAed : Button = root.findViewById(R.id.btn_aed)
        btnAed.setOnClickListener(){
            val aedIntent = Intent(activity, EmergencyAEDActivity::class.java)
            aedIntent.putExtra("What", 1)
            startActivity(aedIntent)
        }
        return root
    }
}