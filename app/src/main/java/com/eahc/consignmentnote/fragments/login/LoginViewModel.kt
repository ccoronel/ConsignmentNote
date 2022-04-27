package com.eahc.consignmentnote.fragments.login

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eahc.consignmentnote.Program
import com.eahc.consignmentnote.entities.ConnObj
import java.util.*

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {
    fun setActiveConnection(): LiveData<ConnObj> = loginRepository.setActiveConnection()

    fun login(unit: String, user: String){
        loginRepository.setLogin(unit, user)
    }

    fun removeExpired(fragment: LoginFragment) {
        loginRepository.removeAllExpired().observe(
            fragment.viewLifecycleOwner,
            {
                consignmentList ->
                var consignErased = 0
                consignmentList.forEach {
                    val iSubtract = -Program.getExpiration(fragment.requireContext())

                    var dt = Date()
                    val c = Calendar.getInstance()
                    c.time = dt
                    c.add(Calendar.DATE, iSubtract)
                    dt = c.time

                    if(it.storedDate.before(dt)) {
                        loginRepository.removeConsignment(it)
                        loginRepository.deleteFile(it.consignment, fragment.requireContext())
                        consignErased++
                    }
                }

                if(consignErased > 0){
                    Toast.makeText(fragment.requireContext(), "Se borraron ${consignErased} cartas porte expiradas", Toast.LENGTH_LONG).show()
                }
            }
        )
    }
}