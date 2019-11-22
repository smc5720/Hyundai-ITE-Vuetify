package kau.msproject.searchaed.ui.emergencyCPR

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kau.msproject.searchaed.R

class CprFifthFragment : Fragment() {
    companion object {
        fun newInstance() = CprFifthFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_cpr_fifth, container, false)
        return root
    }
}