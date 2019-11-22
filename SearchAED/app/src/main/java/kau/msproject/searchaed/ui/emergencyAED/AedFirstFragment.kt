package kau.msproject.searchaed.ui.emergencyAED

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kau.msproject.searchaed.R

class AedFirstFragment() : Fragment() {

    companion object {
        fun newInstance() = AedFirstFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.aed_fragment_first, container, false)
        return root
    }
}