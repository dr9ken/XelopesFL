package com.example.xelopesfl.files.activitys

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.xelopesfl.files.dialogs.DeleteFileDialog
import com.example.xelopesfl.files.dialogs.EmptyFilesDialog
import com.example.xelopesfl.files.dialogs.IdenticalFilesDialog
import com.example.xelopesfl.R
import com.example.xelopesfl.files.algorithms.AlgorithmSettings
import kotlinx.android.synthetic.main.activity_file_selection.*

/**
 * @author Maxim Kolpaschikov
 */

class FileSelectionActivity : AppCompatActivity() {

    private var pathList: ArrayList<String> = ArrayList()
    private var filesList: ArrayList<String> = ArrayList()

    private lateinit var viewAdapter: ArrayAdapter<String>
    private val fileIntent = Intent(Intent.ACTION_GET_CONTENT)

    /**
     * Activation of activity.
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_selection)
        supportActionBar?.hide()
        initFilesList()
    }

    /**
     * Defining the initial list of files and defining button functions.
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private fun initFilesList() {

        // creating an adapter for ListView
        viewAdapter = ArrayAdapter(this, R.layout.file_select_list, filesList)
        viewAdapter.add(getString(R.string.file_selection_search))
        files_list_view.adapter = viewAdapter

        // initialization of the buttons list
        files_list_view.setOnItemClickListener { parent, view, position, id ->
            onClickListBtn(position)
        }

        // initializing the 'next' button
        next_btn.setOnClickListener {
            onClickNextBtn()
        }
    }

    /**
     * Search for a file.
     * @param position - button position in the list
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private fun onClickListBtn(position: Int) {

        if (position == filesList.size - 1) {
            onClickSearch()
        } else {
            onClickDelete(position)
        }
    }

    /**
     * Switching to reading files.
     */
    private fun onClickNextBtn() {

        when (pathList.size) {
            0 -> {
                val emptyFilesDialog = EmptyFilesDialog()
                emptyFilesDialog.show(supportFragmentManager, "EmptyFilesDialog")
            }
            else -> {
                val intent = createSettingsIntent()
                startActivity(intent)
            }
        }
    }

    /**
     * Creating intent for settings-activity.
     */
    private fun createSettingsIntent() : Intent {

        val settings = AlgorithmSettings()
        settings.paths = pathList

        intent = Intent(this, SettingsActivity::class.java)
        intent.putExtra(AlgorithmSettings::class.java.simpleName, settings)
        return intent
    }

    /**
     * Adding a file to the list.
     */
    private fun onClickSearch() {

        fileIntent.type = "*/*"
        this.startActivityForResult(
            Intent.createChooser(
                fileIntent,
                getString(R.string.file_selection_heading)
            ), 1
        )
    }

    /**
     * Deleting a file from the list.
     * @param position - button position in the list
     */
    private fun onClickDelete(position: Int) {

        val deleteDialog = DeleteFileDialog(
            position,
            pathList,
            filesList,
            viewAdapter
        )
        deleteDialog.show(supportFragmentManager, "DeleteDialog")
    }

    /**
     * Saving the file path.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            val path = getPath(data?.data?.path ?: return)
            val fileName = getFileName(path)

            if(filesList.contains(fileName)) {
                val identicalFilesDialog = IdenticalFilesDialog(
                    viewAdapter,
                    pathList,
                    fileName,
                    path
                )
                identicalFilesDialog.show(supportFragmentManager, "IdenticalFilesDialog")
            } else {
                pathList.add(path)
                viewAdapter.insert(fileName, pathList.size - 1)
            }
        }
    }

    /**
     * Re-forms the path to the library file.
     * @param path - the path from which the file name is extracted
     */
    private fun getPath(path: String): String {


        val sdcardDir = "/sdcard/"
        val primaryDir = "/storage/self/"
        val filePath = path.removePrefix("/document/")

        return when(filePath.substringBefore(':')) {
            "sdcard" -> sdcardDir + filePath.replace(':', '/')
            "primary" -> primaryDir + filePath.replace(':', '/')
            else -> ""
        }
    }

    /**
     *  Retrieves the file name from its path.
     *  @param path - the path from which the file name is extracted
     */
    private fun getFileName(path: String): String {

        var fileName = String()
        var isFileName = false
        for (char in path.reversed()) {
            if (char == '.') {
                isFileName = true
                continue
            }
            if (char == '/') break
            if (isFileName) fileName += char
        }
        return fileName.reversed()
    }
}