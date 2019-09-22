package com.example.msproject

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var create=findViewById<Button>(R.id.create)
        val remove : Button = findViewById(R.id.remove)
        create.setOnClickListener(View.OnClickListener {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            override fun onClick(view : View){
                    createNotification()
                }
        })
        remove.setOnClickListener(View.OnClickListener {
            override fun onClick(view : View){
                removeNotification()
            }
        })
    }

    fun createNotification(){
        //object builder<NotificationCompat.Builder>=NotificationCompat.Builder(this,"default")
        val builder : NotificationCompat.Builder = NotificationCompat.Builder(this,"default")


    }
    fun removeNotification(){
        
    }

}
