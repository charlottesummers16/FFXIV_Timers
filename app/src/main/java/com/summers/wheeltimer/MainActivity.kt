package com.summers.wheeltimer

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MAIN_ACTIVITY"
    }

    private val frag = supportFragmentManager.findFragmentByTag("fragment")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadWheelFragment()



        findViewById<TabLayout>(R.id.tabLayout).addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                //Do nothing
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {loadWheelFragment()/*Wheel*/}
                    1 -> {loadGardensFragment()/*Gardens*/}
                    2 -> {/*Pots*/}
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //Do nothing
            }
        })

    }

    private fun loadWheelFragment() {
        if (frag != null) {
            supportFragmentManager.beginTransaction()
                .remove(frag!!)
                .commit()
        }
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .add(R.id.clFragmentHolder, WheelFragment.newInstance(), "fragment")
            .addToBackStack("TimerBackstack")
            .commit()
    }

    private fun loadGardensFragment() {
        if (frag != null) {
            supportFragmentManager.beginTransaction()
                    .remove(frag!!)
                    .commit()
        }
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
                .add(R.id.clFragmentHolder, GardenPatchOneFragment.newInstance(), "fragment")
                .addToBackStack("TimerBackstack")
                .commit()
    }

}