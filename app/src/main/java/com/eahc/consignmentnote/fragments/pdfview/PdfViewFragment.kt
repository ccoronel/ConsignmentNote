package com.eahc.consignmentnote.fragments.pdfview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.eahc.consignmentnote.Program
import com.eahc.consignmentnote.R
import com.eahc.consignmentnote.databinding.FragmentPdfViewBinding
import java.io.File

private const val ARG_PARAM1 = "consignmentNo"

class PdfViewFragment : Fragment() {
    private var consignmentNo: String? = null
    private lateinit var mBinding: FragmentPdfViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            consignmentNo = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_pdf_view, container, false)

        mBinding.pdfVViewModel =
            ViewModelProvider(this).get(PdfVViewModel::class.java)

        if (consignmentNo != null) {
            openPDF(consignmentNo.toString())
        }

        return mBinding.root
    }

    private fun openPDF(consignmentNo: String) {
        val fPath = "${Program.getWorkPath(requireContext())}/consignments/${consignmentNo}.pdf"
        val file = File(fPath)

        mBinding.pdfView.fromFile(file).onError {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage(it.message)
            val alert = builder.create()
            alert.show()
            findNavController().popBackStack()
        }.load()
    }

}