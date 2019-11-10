package kau.msproject.searchaed.ui.emergency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import kau.msproject.searchaed.EmergencyActivity
import kau.msproject.searchaed.R
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class FirstFragment() : Fragment() {

    companion object {
        fun newInstance() = FirstFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_first, container, false)
        return root




    }
}