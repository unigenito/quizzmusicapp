package com.usj.musicquizz

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import com.usj.musicquizz.databinding.ActivityMainBinding
import com.usj.musicquizz.model.QuizzViewModel
import com.usj.musicquizzapi.api.SongsServiceApi
import com.usj.musicquizzapi.invoker.ApiException
import com.usj.musicquizzapi.model.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : AppCompatActivity(), AnswersListener {
    private var isReady:Boolean = false
    private lateinit var songs: MutableList<Song>
    private lateinit var songsVM: QuizzViewModel

    private val view by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val songsServiceApi by lazy {
        val api = SongsServiceApi()
        api.customBaseUrl = "http://${getString(R.string.host)}:8080"
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

        songsVM = ViewModelProvider(this)[QuizzViewModel::class.java]

        // Set up an OnPreDrawListener to the root view.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check whether the initial data is ready.
                    return if (isReady) {
                        songsVM.mutableList.value = songs
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

    private  fun replaceFragment(fragment: Fragment){
        findViewById<FragmentContainerView>(R.id.fragment_container)
            ?.let {
                    frameLayout ->
                supportFragmentManager.beginTransaction()
                    .replace(frameLayout.id, fragment)
                    .commit()
            }
    }



    private fun searchSongs(){
        try {

            scope.launch {
                songs = songsServiceApi.findAll()
                if (songs.isNotEmpty()){

                    isReady = true
                    replaceFragment(HomeFragment())

                }
                print(songs)
            }
        }
        catch (e: ApiException){
            Log.d(e.toString(), e.message.toString())
            isReady = true
        }
        catch (e:IOException){
            isReady = true
            Toast.makeText(this, "There was an error getting songs.", Toast.LENGTH_LONG).show()
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
        songsVM.setName(name)
        Toast.makeText(this, name, Toast.LENGTH_LONG).show()
        replaceFragment(QuizFragment.newInstance(name))
    }
}