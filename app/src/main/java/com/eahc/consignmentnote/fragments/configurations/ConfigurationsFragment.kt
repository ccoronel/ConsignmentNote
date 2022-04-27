package com.eahc.consignmentnote.fragments.configurations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eahc.consignmentnote.AppDatabase
import com.eahc.consignmentnote.Program
import com.eahc.consignmentnote.R
import com.eahc.consignmentnote.adapters.ConnectionListAdapter
import com.eahc.consignmentnote.databinding.FragmentConfigurationsBinding
import com.eahc.consignmentnote.dialog.ConnectDialog
import com.eahc.consignmentnote.entities.ConnObj


class ConfigurationsFragment : Fragment() {

    private lateinit var mBinding: FragmentConfigurationsBinding
    private lateinit var db: AppDatabase
    private lateinit var factory: ConfigurationFactory
    private lateinit var repository: ConfigurationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (Program.hasConnAssigned()) {
                        if (isEnabled) {
                            isEnabled = false
                            requireActivity().onBackPressed()
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Se debe seleccionar un perfil de conexión antes de salir",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_configurations, container, false)

        db = AppDatabase(requireContext())
        repository = ConfigurationRepository(db)
        factory = ConfigurationFactory(repository)
        mBinding.configurationViewModel =
            ViewModelProvider(this, factory).get(ConfigurationVewModel::class.java)

        mBinding.listProfiles.layoutManager = LinearLayoutManager(requireContext())

        mBinding.configurationViewModel?.list = mBinding.configurationViewModel?.getConnections()

        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            this.resources.getString(R.string.titleConfigurations)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.configurationViewModel?.list?.observe(
            viewLifecycleOwner, {
                val listAdapter = ConnectionListAdapter(it)
                listAdapter.notifyDataSetChanged()
                mBinding.listProfiles.adapter = listAdapter

                val adapter =  ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it)
                adapter.notifyDataSetChanged()
                mBinding.currentProfile.adapter = adapter


                val active = it.find { connObj -> connObj.Active }
                if (active != null) {
                    Program.setConnObj(active)
                }
                if (it.isEmpty()) {
                    if (Program.hasConnAssigned()) {
                        Program.removeConnObj()
                    }
                }
            }
        )

        mBinding.btnClear.setOnClickListener {
            if (mBinding.listProfiles.adapter != null) {
                val adapter = mBinding.listProfiles.adapter as ConnectionListAdapter
                if (adapter.selectedList.size > 0) {
                    adapter.selectedList.forEach {
                        mBinding.configurationViewModel?.removeConnection(it)
                    }
                } else {
                    Toast.makeText(context, "No hay conexiones a borrar", Toast.LENGTH_LONG).show()
                }
            }
        }

        mBinding.btnAdd.setOnClickListener {
            val dialog = ConnectDialog(requireContext(), mBinding)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.message_dialog_add_connection)
            dialog.show()
        }

        mBinding.currentProfile.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val selectedObject = mBinding.currentProfile.selectedItem as ConnObj
                    mBinding.configurationViewModel?.seActiveConnection(selectedObject)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

    }

}