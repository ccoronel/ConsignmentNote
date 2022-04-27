package com.eahc.consignmentnote.dialog

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.Toast
import com.eahc.consignmentnote.R
import com.eahc.consignmentnote.databinding.FragmentConfigurationsBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlin.concurrent.thread

class ConnectDialog(context: Context, private val binding: FragmentConfigurationsBinding) :
    Dialog(context) {


    override fun show() {
        buildMessage()
        super.show()
    }

    private fun buildMessage() {
        val profile = this.findViewById<TextInputEditText>(R.id.profile)
        this.findViewById<TextInputLayout>(R.id.urlLayout)
        val url = this.findViewById<TextInputEditText>(R.id.url)
        val username = this.findViewById<TextInputEditText>(R.id.user)
        val password = this.findViewById<TextInputEditText>(R.id.password)
        val button = this.findViewById<Button>(R.id.btnContinue)

        button.setOnClickListener {

            if (profile.text.toString().isNotEmpty() && url.text.toString().isNotEmpty() && username.text.toString().isNotEmpty() && password.text.toString().isNotEmpty()) {
                val findConn = binding.configurationViewModel?.list?.value?.find { connObj -> connObj.profile == profile.text.toString() }
                if (findConn != null) {
                    Toast.makeText(context, "El nombre de perfil ya existe.", Toast.LENGTH_LONG)
                        .show()
                    profile.setText("")
                    profile.requestFocus()
                } else {
                    thread {
                        binding.configurationViewModel?.addConnection(
                            profile.text.toString(),
                            url.text.toString(),
                            username.text.toString(),
                            password.text.toString(),
                            binding.configurationViewModel?.list?.value?.isEmpty() == true
                        )
                        binding.listProfiles.post {
                            binding.configurationViewModel?.list =
                                binding.configurationViewModel?.getConnections()
                            binding.listProfiles.adapter?.notifyDataSetChanged()
                        }
                    }

                    dismiss()
                }
            } else {
                Toast.makeText(
                    context,
                    "Todos los campos son requeridos, verifique sus entradas.",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }

}