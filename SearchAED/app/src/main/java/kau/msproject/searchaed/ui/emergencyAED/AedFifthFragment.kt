package kau.msproject.searchaed.ui.emergencyAED

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kau.msproject.searchaed.R

class AedFifthFragment() : Fragment() {

    companion object {
        fun newInstance() = AedFifthFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_aed_fifth, container, false)
        return root
    }
}