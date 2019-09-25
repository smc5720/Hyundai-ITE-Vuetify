package com.example.msproject

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Build.VERSION_CODES
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

class MainActivity : AppCompatActivity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val create=findViewById<Button>(R.id.create)
        val remove : Button = findViewById(R.id.remove)
        create.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v : View?){
                    createNotification()
                }
        })
        remove.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View?){
                removeNotification()
            }
        })
    }
    //123213213213214124123
    //12312521153326234234324

    fun createNotification(){
        val builder : NotificationCompat.Builder = NotificationCompat.Builder(this,"default")
        builder.setSmallIcon(R.mipmap.ic_launcher) //아이곤
        builder.setContentTitle("제목") //세부제목
        builder.setContentText("내용") //세부내용
        builder.setColor(Color.RED) //알림 색깔?
        builder.setAutoCancel(true)     //사용자가 탭을 클릭하면 자동 닫기
        //알림생성
        val notificationManager : NotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            notificationManager.createNotificationChannel(NotificationChannel("default","기본채널",NotificationManager.IMPORTANCE_DEFAULT))
        }
        // id값은
        // 정의해야하는 각 알림의 고유한 int값
        notificationManager.notify(1, builder.build())
    }
    fun removeNotification(){
        NotificationManagerCompat.from(this).cancel(1);
    }

}
