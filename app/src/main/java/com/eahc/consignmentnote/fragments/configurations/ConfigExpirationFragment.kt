package com.eahc.consignmentnote.fragments.configurations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.eahc.consignmentnote.Program
import com.eahc.consignmentnote.R
import com.eahc.consignmentnote.databinding.FragmentConfigExpirationBinding


class ConfigExpirationFragment : Fragment() {

    lateinit var mBinding: FragmentConfigExpirationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_config_expiration, container, false)

        mBinding.iExpiration.setText(Program.getExpiration(requireContext()).toString())
        mBinding.btnConfirm.setOnClickListener {
            if(mBinding.iExpiration.text?.isNotEmpty() == true){
                Program.setExpiration(mBinding.iExpiration.text.toString().toInt(), requireContext())
            }
        }

         return mBinding.root
    }

}