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
import com.noox.wordscount.words.ui.WordsAdapter.SortType.*
import initBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

// TODO: Ahora usamos coroutinas en la Activity, luego se usaran desde el ViewModel y la
// Activity observara la palabras
class MainActivity : AppCompatActivity(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val adapter = WordsAdapter()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = initBinding(R.layout.activity_main)

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
        else -> super.onOptionsItemSelected(item)
    }

    private fun words() = flow {
        val words = arrayOf("Pedro", "Alicia", "Carlos", "alicia", "pedro", "Pedro", "Alicia", "Carlos", "alicia", "pedro", "Pedro", "Alicia", "Carlos", "alicia", "pedro")
        words.forEach {
            emit(it)
        }
    }

}
