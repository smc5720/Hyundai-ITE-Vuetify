package com.example.searchaed.ui.youtube

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.searchaed.R


class YoutubeFragment : Fragment() {

    companion object {
        fun newInstance() = YoutubeFragment()
    }

    private lateinit var youtubeViewModel: YoutubeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        youtubeViewModel = ViewModelProviders.of(this).get(YoutubeViewModel::class.java)
        val root = inflater.inflate(R.layout.youtube_fragment, container, false)

        val cprLink: TextView = root.findViewById(R.id.cpr_link)
        val aedLink: TextView = root.findViewById(R.id.aed_link)

        youtubeViewModel.cpr_text.observe(this, Observer {
            cprLink.text = it
        })
        youtubeViewModel.aed_text.observe(this, Observer {
            aedLink.text = it
        })

        cprLink.setOnClickListener(View.OnClickListener {
            val intent : Intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse("https://www.youtube.com/watch?v=zWv7xsm0Tu8"))
            startActivity(intent)
        })

        aedLink.setOnClickListener(View.OnClickListener {
            val intent : Intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse("https://www.youtube.com/watch?v=ViZtrjdwY9I"))
            startActivity(intent)
        })

        return root
    }

    fun onBackPressed(){
        
    }

}
