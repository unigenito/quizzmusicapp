package com.usj.musicquizz

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.fragment.app.FragmentContainerView
import com.usj.musicquizzapi.api.SongsServiceApi
import com.usj.musicquizz.databinding.ActivityMainBinding
import com.usj.musicquizzapi.model.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : AppCompatActivity(), AnswersListener {
var isReady:Boolean = false
    val songs: MutableList<Song>? = null
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
        val splash = installSplashScreen()
        splash.setOnExitAnimationListener { splashScreenView ->
            animateSplashScreen(splashScreenView)
        }


        super.onCreate(savedInstanceState)
        // Set up an OnPreDrawListener to the root view.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check whether the initial data is ready.
                    return if (isReady) {
                        // The content is ready. Start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content isn't ready. Suspend.
                        false
                    }
                }
            }
        )

        setContentView(view.root)
        //splash.setKeepOnScreenCondition{ true }
        searchSongs()
    }

    override fun onStart() {
        super.onStart()
        //isReady = true
    }

    private fun searchSongs(){
        try {
            scope.launch {
                val songs = songsServiceApi.findAll()
                if (songs.isNotEmpty()){
                    isReady = true
                    findViewById<FragmentContainerView>(R.id.fragmet_container)
                        ?.let {
                            frameLayout ->
                            val homeFragment = HomeFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(frameLayout.id, homeFragment)
                            .commit()
                    }
                }
                print(songs)
            }
        }catch (e:IOException){

        }
    }

    private fun animateSplashScreen(splashScreenView: SplashScreenViewProvider){
        // Create your custom animation.
        val slideUp = ObjectAnimator.ofFloat(
            splashScreenView.view,
            View.TRANSLATION_Y,
            0f,
            -splashScreenView.view.height.toFloat()
        )

        slideUp.interpolator = AnticipateInterpolator()
        slideUp.duration = 200L

        // Call SplashScreenView.remove at the end of your custom animation.
        slideUp.doOnEnd { splashScreenView.remove() }

        // Run your animation.
        slideUp.start()
    }

    override fun onSelected(questionId: Int, name:String) {
        Toast.makeText(this, name, Toast.LENGTH_LONG).show()
    }
}