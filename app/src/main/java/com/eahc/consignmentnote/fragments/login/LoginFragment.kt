package com.eahc.consignmentnote.fragments.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.eahc.consignmentnote.AppDatabase
import com.eahc.consignmentnote.Program
import com.eahc.consignmentnote.R
import com.eahc.consignmentnote.databinding.FragmentLoginBinding
import com.eahc.consignmentnote.dialog.ConfigPasswordDialog

class LoginFragment : Fragment() {

    private lateinit var mBinding: FragmentLoginBinding
    private lateinit var factory: LoginFactory
    private lateinit var repository: LoginRepository
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            this.resources.getString(R.string.titleLogin)

        db = AppDatabase(requireContext())
        repository = LoginRepository(db)
        factory = LoginFactory(repository)
        mBinding.loginVModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        mBinding.loginVModel?.setActiveConnection()?.observe(viewLifecycleOwner, {
            if(it == null){
                getConfigTabs()
            } else {
                Program.setConnObj(it)
                val text = this.resources.getString(R.string.conection) + ": " + it.profile
                mBinding.conName.text = text
            }
        })

        mBinding.btnLogin.setOnClickListener {
                if (mBinding.unit.text.isNotEmpty() && mBinding.user.text.isNotEmpty()) {
                    mBinding.loginVModel?.login(
                        mBinding.unit.text.toString(),
                        mBinding.user.text.toString()
                    )

                    findNavController().navigate(R.id.action_loginFragment_to_addConsignmentsFragment)
                    mBinding.unit.text.clear()
                    mBinding.user.text.clear()
                }
        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btnConfig.setOnClickListener {
            getConfigTabs()
        }

        if(Program.getExpiration(requireContext()) > 0){
            mBinding.loginVModel?.removeExpired(this)
        }

        mBinding.unit.requestFocus()
    }

    fun getConfigTabs(){
        val dialog = ConfigPasswordDialog()
        dialog.showDialog(this)
    }
}