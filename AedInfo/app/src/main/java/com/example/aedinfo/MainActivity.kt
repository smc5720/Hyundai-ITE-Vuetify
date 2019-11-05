package com.example.aedinfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var aed : Aed = Aed("경기도 고양시 덕양구 항공대학로 76 2층 어디어디앞",
            "010-4180-1255",
            "3km")

        val button : Button = findViewById(R.id.btn)
        button.setOnClickListener(View.OnClickListener {view ->
            val detailIntent = Intent(this,AedInfoPopup::class.java)
            detailIntent.putExtra("Aed", aed)
            startActivityForResult(detailIntent,1)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


}
