package com.noox.wordscount.words.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.noox.wordscount.R
import com.noox.wordscount.databinding.ActivityMainBinding
import initBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

// TODO: Ahora usamos coroutinas en la Activity, luego se usaran desde el ViewModel y la
// Activity observara la palabras
class MainActivity : AppCompatActivity(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private lateinit var binding: ActivityMainBinding

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = initBinding(R.layout.activity_main)

        val adapter = WordsAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, VERTICAL))
        binding.recyclerView.adapter = adapter

        launch {
            words().collect {
                adapter.add(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_sort_alphabetical -> {
            Toast.makeText(this, "sort alphabetical", Toast.LENGTH_SHORT).show()
            true
        }
        R.id.menu_sort_position -> {
            Toast.makeText(this, "sort escending", Toast.LENGTH_SHORT).show()
            true
        }
        R.id.menu_sort_appearances -> {
            Toast.makeText(this, "sort numerical", Toast.LENGTH_SHORT).show()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun words() = flow {
        val words = arrayOf("Pedro", "Alicia", "Carlos", "alicia", "pedro", "Pedro", "Alicia", "Carlos", "alicia", "pedro", "Pedro", "Alicia", "Carlos", "alicia", "pedro")
        words.forEach {
            emit(it)
        }
    }

}
