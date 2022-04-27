package com.eahc.consignmentnote.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eahc.consignmentnote.R

class ConfigPasswordDialog {

    fun showDialog(fragment: Fragment) {
        val dialog = Dialog(fragment.requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.message_dialog_config_password)
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        val password: EditText = dialog.findViewById(R.id.password)
        val btnConfirm: Button = dialog.findViewById(R.id.btnConfirm)

        btnConfirm.setOnClickListener {
            if(password.text.isNotEmpty() && password.text.toString() == "SyS&2021"){
                fragment.findNavController().navigate(R.id.action_loginFragment_to_configTabs)
                dialog.dismiss()
            } else {
                val alert = AlertDialog.Builder(fragment.requireContext())
                alert.setMessage("Password incorrecto")
                alert.setCancelable(true)
                alert.show()
                dialog.dismiss()
            }
        }

        dialog.show()
    }
}