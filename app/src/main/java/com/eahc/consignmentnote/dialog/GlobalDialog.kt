package com.eahc.consignmentnote.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import com.eahc.consignmentnote.R

open class GlobalDialog(context: Context, private var msgType: String, private var responseMessage: String) : Dialog(context) {

    private lateinit var soundPath: Uri

    override fun show() {
        setMessageType()
        buildMessage()
        launchSound()
        super.show()
    }

    private fun launchSound() {
        try {
            val r = RingtoneManager.getRingtone(
                context,
                soundPath
            )
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun buildMessage() {
        val text = this.findViewById<TextView>(R.id.text_dialog)
        val imageView = this.findViewById<ImageView>(R.id.headerImage)

        if (msgType == "" || msgType == "E") {
            imageView.setBackgroundColor(Color.RED)
            //imageView.setImageResource(R.drawable.ic_report_problem_black_24dp)
        } else if (msgType == "W") {
            imageView.setBackgroundColor(Color.YELLOW)
        } else {
            //imageView.setImageResource(R.drawable.ic_arrowq)
            imageView.setBackgroundColor(Color.GREEN)
        }

        text.text = responseMessage
    }

    private fun setMessageType() {
        soundPath = if (msgType == "E" || msgType == "" || msgType == "W") {
            Uri.parse("android.resource://" + context.packageName + "/" + R.raw.erro)
        } else {
            Uri.parse("android.resource://" + context.packageName + "/" + R.raw.success)
        }
    }
}