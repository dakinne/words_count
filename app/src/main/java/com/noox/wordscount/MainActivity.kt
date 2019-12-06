package com.noox.wordscount

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.noox.wordscount.databinding.ActivityMainBinding
import initBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

// TODO: Ahora usamos coroutinas en la Activity, luego se usaran desde el ViewModel y la
// Activity observara la palabras
class MainActivity : AppCompatActivity(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private lateinit var binding: ActivityMainBinding

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

    private fun words() = flow {
        val words = arrayOf("Pedro", "Alicia", "Carlos", "alicia", "pedro", "Pedro", "Alicia", "Carlos", "alicia", "pedro", "Pedro", "Alicia", "Carlos", "alicia", "pedro")
        words.forEach {
            emit(it)
            delay(100)
        }
    }

}
