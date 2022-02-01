package com.summers.wheeltimer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class GardenPatchOneFragment : Fragment() {

    companion object {
        const val TAG = "GARDEN_PATCH_ONE_FRAGMENT"
        private const val FILE_KEY = "GARDEN_FILE_KEY"
        fun newInstance() : GardenPatchOneFragment{
            return GardenPatchOneFragment()
        }
    }

    private lateinit var sharedPref: SharedPreferences
    private lateinit var fragmentView: View
    private lateinit var fragmentContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        fragmentView.findViewById<ImageView>(R.id.iv_1_1).setOnClickListener { loadPlantDetailsPopup() }
//        fragmentView.findViewById<ImageView>(R.id.iv_1_2).setOnClickListener { loadPlantDetailsPopup() }
//        fragmentView.findViewById<ImageView>(R.id.iv_1_3).setOnClickListener { loadPlantDetailsPopup() }
//        fragmentView.findViewById<ImageView>(R.id.iv_1_4).setOnClickListener { loadPlantDetailsPopup() }
//        fragmentView.findViewById<ImageView>(R.id.iv_1_5).setOnClickListener { loadPlantDetailsPopup() }
//        fragmentView.findViewById<ImageView>(R.id.iv_1_6).setOnClickListener { loadPlantDetailsPopup() }
//        fragmentView.findViewById<ImageView>(R.id.iv_1_7).setOnClickListener { loadPlantDetailsPopup() }
//        fragmentView.findViewById<ImageView>(R.id.iv_1_8).setOnClickListener { loadPlantDetailsPopup() }
//
//        fragmentView.findViewById<ImageView>(R.id.iv_2_1).setOnClickListener { loadPlantDetailsPopup() }
//        fragmentView.findViewById<ImageView>(R.id.iv_2_2).setOnClickListener { loadPlantDetailsPopup() }
//        fragmentView.findViewById<ImageView>(R.id.iv_2_3).setOnClickListener { loadPlantDetailsPopup() }
//        fragmentView.findViewById<ImageView>(R.id.iv_2_4).setOnClickListener { loadPlantDetailsPopup() }
//        fragmentView.findViewById<ImageView>(R.id.iv_2_5).setOnClickListener { loadPlantDetailsPopup() }
//        fragmentView.findViewById<ImageView>(R.id.iv_2_6).setOnClickListener { loadPlantDetailsPopup() }
//        fragmentView.findViewById<ImageView>(R.id.iv_2_7).setOnClickListener { loadPlantDetailsPopup() }
//        fragmentView.findViewById<ImageView>(R.id.iv_2_8).setOnClickListener { loadPlantDetailsPopup() }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentContext = container!!.context
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_garden_patch_one, container, false)
        return fragmentView
    }

    private fun loadPlantDetailsPopup() {

    }

}