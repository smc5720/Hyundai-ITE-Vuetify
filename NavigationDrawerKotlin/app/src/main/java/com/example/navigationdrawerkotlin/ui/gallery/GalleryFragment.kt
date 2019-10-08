package com.example.navigationdrawerkotlin.ui.gallery

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
import com.example.navigationdrawerkotlin.R
import com.example.navigationdrawerkotlin.ui.home.HomeViewModel
import org.w3c.dom.Text

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =

            ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)

        val cprLink: TextView = root.findViewById(R.id.cpr_link)
        val aedLink: TextView = root.findViewById(R.id.aed_link)

        galleryViewModel.cpr_text.observe(this, Observer {
            cprLink.text = it
        })
        galleryViewModel.aed_text.observe(this, Observer {
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
}