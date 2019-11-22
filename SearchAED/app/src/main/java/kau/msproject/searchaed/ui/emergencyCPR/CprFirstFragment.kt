package kau.msproject.searchaed.ui.emergencyCPR

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kau.msproject.searchaed.R

class CprFirstFragment() : Fragment() {

    companion object {
        fun newInstance() = CprFirstFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.cpr_fragment_first, container, false)
        return root
    }
}