package com.example.xelopesfl.files.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.example.xelopesfl.R

/**
 * @author Maxim Kolpaschikov
 */

class IdenticalFilesDialog(private var _viewAdapter : ArrayAdapter<String>,
                           private var _pathList : ArrayList<String>,
                           private val _fileName : String,
                           private val _path: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val dialogBuilder = AlertDialog.Builder(it)
            dialogBuilder
                .setTitle(getString(R.string.identical_file_dialog_heading))
                .setMessage(getString(R.string.identical_file_dialog_msg))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.identical_file_dialog_no_btn)) { dialogInterface, i ->
                    dialogInterface.cancel()
                }
                .setNegativeButton(getString(R.string.identical_file_dialog_yes_btn)) { dialogInterface, i ->
                    dialogInterface.cancel()
                    _pathList.add(_path)
                    _viewAdapter.insert(_fileName, _pathList.size - 1)
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}