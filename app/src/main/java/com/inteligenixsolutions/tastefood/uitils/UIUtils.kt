package com.inteligenixsolutions.tastefood.uitils

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.inteligenixsolutions.tastefood.R

fun showProgressDialog(context: Context, message: String): Dialog {
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.progress_bar)
    dialog.setCancelable(false)

    val messageTextView: TextView = dialog.findViewById(R.id.messageTextView)
    val progressBar: ProgressBar = dialog.findViewById(R.id.customProgresBar)
    val color = ContextCompat.getColor(context, R.color.green)
    progressBar.indeterminateTintList = ColorStateList.valueOf(color)

    messageTextView.text = message
    progressBar.isIndeterminate = true

    dialog.show()

    return dialog
}

fun dismissProgressDialog(dialog: Dialog?) {
    dialog?.dismiss()
}

