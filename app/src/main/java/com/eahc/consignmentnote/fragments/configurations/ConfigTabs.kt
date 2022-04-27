package com.eahc.consignmentnote.fragments.configurations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.eahc.consignmentnote.R
import com.eahc.consignmentnote.adapters.ConfigViewPageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ConfigTabs : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_config_tabs, container, false)

        val tabLayout = rootView.findViewById<TabLayout>(R.id.configTabLayout)
        val viewPager = rootView.findViewById<ViewPager2>(R.id.configViewPager)

        val adapter = ConfigViewPageAdapter(this.requireActivity())
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager){
                tab, position ->
            when(position){
                0 -> {
                    tab.text = requireContext().getString(R.string.titleConnections)
                }
                1 -> {
                    tab.text = requireContext().getString(R.string.titleExpiration)
                }
            }
        }.attach()

        return rootView
    }

}