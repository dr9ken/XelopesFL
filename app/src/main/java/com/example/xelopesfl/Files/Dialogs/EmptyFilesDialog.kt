package com.example.xelopesfl.Files.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.xelopesfl.R

class EmptyFilesDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity.let {
            val dialogBuilder = AlertDialog.Builder(it)
            dialogBuilder
                .setTitle(getString(R.string.empty_files_dialog_heading))
                .setMessage(getString(R.string.empty_files_dialog_msg))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.empty_files_dialog_ok_btn)) { dialogInterface, i ->
                    dialogInterface.cancel()
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}