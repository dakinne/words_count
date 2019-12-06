package com.noox.wordscount.words.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.VISIBLE
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.noox.wordscount.R
import com.noox.wordscount.databinding.ActivityMainBinding
import initBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion

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
            words()
                .onCompletion {
                    binding.fab.visibility = VISIBLE
                }.collect {
                adapter.add(it)
            }
        }

        binding.fab.setOnClickListener { showBottomSheet() }
    }

    private fun showBottomSheet() {
        WordsBottomSheetFragment().let {
            it.show(supportFragmentManager, it.tag)
        }
    }

    private fun words() = flow {
        val words = arrayOf("Pedro", "Alicia", "Carlos", "alicia", "pedro", "Pedro", "Alicia", "Carlos", "alicia", "pedro", "Pedro", "Alicia", "Carlos", "alicia", "pedro")
        words.forEach {
            emit(it)
        }
    }

}
