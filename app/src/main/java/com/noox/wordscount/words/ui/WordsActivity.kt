package com.noox.wordscount.words.ui

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.noox.wordscount.R
import com.noox.wordscount.databinding.ActivityMainBinding
import com.noox.wordscount.words.ui.WordsAdapter.SortType.*
import initBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.BufferedReader

class WordsActivity : AppCompatActivity(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val adapter = WordsAdapter()

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<WordsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = initBinding(R.layout.activity_main)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, VERTICAL))
        binding.recyclerView.adapter = adapter

        viewModel.words.observe(this, Observer { render(it) })

        launch {
            words().collect {
                adapter.add(it)
            }
        }
    }

    private fun render(words: Words) {
        Log.i("NOOX", "render INI")
        words.items.forEach {
            Log.i("NOOX", "$it")
        }
        Log.i("NOOX", "render FIN")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_sort_alphabetical -> {
            adapter.sortBy(Alphabetical)
            true
        }
        R.id.menu_sort_position -> {
            adapter.sortBy(Position)
            true
        }
        R.id.menu_sort_appearances -> {
            adapter.sortBy(Appearance)
            true
        }
        R.id.menu_file -> {
            if (permissionsAllowed()) { chooseFile() }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun permissionsAllowed(): Boolean {
        if (checkPermissions()) {
            return true
        }

        requestPermissions()
        return false
    }

    private fun checkPermissions(): Boolean {
        val permissionRequest = ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
        return (permissionRequest == PERMISSION_GRANTED)
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE), 1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(grantResults.isNotEmpty()) {
            var allAllowed = true
            grantResults.forEach {
                if (it != PERMISSION_GRANTED) {
                    allAllowed = false
                }
            }
            if (allAllowed) {
                chooseFile()
            }
        }
    }

    private fun chooseFile() {
        val intent = Intent(ACTION_GET_CONTENT).apply {
            type = "text/plain"
        }
        startActivityForResult(Intent.createChooser(intent, "Selecciona un fichero de texto"), RC_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != RC_FILE || resultCode != RESULT_OK) {
            return
        }

        data?.data?.let {
            viewModel.loadWordsFrom(it)
        }
    }

    private fun importFile(uri: Uri) {

        val byWord = "\\s+".toRegex()

        val inputStream = contentResolver.openInputStream(uri) ?: return
        val reader = BufferedReader(inputStream.reader())

        launch {
            flow {
                reader.useLines { lines ->
                    lines.forEach { line ->
                        line.split(byWord).forEach { word ->
                            emit(word)
                        }
                    }
                }
            }.collect {
                Log.i("NOOX", "collect $it")
            }
        }
    }

    private fun words() = flow {
        val words = arrayOf("Pedro", "Alicia", "Carlos", "alicia", "pedro", "Pedro", "Alicia", "Carlos", "alicia", "pedro", "Pedro", "Alicia", "Carlos", "alicia", "pedro")
        words.forEach {
            emit(it)
        }
    }

    companion object {
        private const val RC_FILE = 1
    }

}
