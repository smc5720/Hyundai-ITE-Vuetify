package kau.msproject.searchaed.ui.emergency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kau.msproject.searchaed.R

class ThirdFragment() : Fragment() {

    companion object {
        fun newInstance() = ThirdFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_third, container, false)
        return root
    }
}