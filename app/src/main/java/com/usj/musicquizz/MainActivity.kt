package com.usj.musicquizz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.usj.musicquizz.api.SongsServiceApi
import com.usj.musicquizz.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val view by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val songsServiceApi by lazy {
        val api = SongsServiceApi()
        api.customBaseUrl = "http://10.0.2.2:8080"
        api
    }

    private val scope by lazy {
        CoroutineScope(Dispatchers.IO)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)
        scope.launch {
            val songs = songsServiceApi.findAll()
            print(songs)
        }
    }
}