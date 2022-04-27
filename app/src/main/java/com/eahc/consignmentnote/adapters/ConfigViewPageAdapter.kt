package com.eahc.consignmentnote.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.eahc.consignmentnote.fragments.configurations.ConfigExpirationFragment
import com.eahc.consignmentnote.fragments.configurations.ConfigurationsFragment

class ConfigViewPageAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                ConfigurationsFragment()
            }
            1 -> {
                ConfigExpirationFragment()
            }
            else -> {
                ConfigurationsFragment()
            }
        }
    }
}