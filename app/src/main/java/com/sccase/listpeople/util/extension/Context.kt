package com.sccase.listpeople.util.extension

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.sccase.listpeople.R

/**
 * Create alert dialog
 */
fun Context.createAlertDialog(
    text: String,
    title: String = "Error",
    yesAction: () -> Unit
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(text)
        .setPositiveButton(
            getStringResource(R.string.txt_yes)
        ) { _, _ -> yesAction() }
        .setNeutralButton(
            getStringResource(R.string.txt_cancel)
        ) { dialog, _ ->
            dialog.dismiss()
        }.setCancelable(false)
        .show()
}

/**
 * Get String resources
 */
fun Context.getStringResource(id: Int): String {
    return this.resources.getString(id)
}