package com.usj.musicquizz

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.usj.musicquizz.model.QuizzViewModel
import com.usj.musicquizzapi.model.Song
import java.io.IOException
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_NAME = "name"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizFragment : Fragment(), View.OnClickListener {
    private lateinit var timer: Timer
    private lateinit var tvTimer: TextView
    private var name: String? = null
    private lateinit var songsVM: QuizzViewModel
    private var timeLeft = 15
    private var opportunities = 4
    private val handler = Handler(Looper.getMainLooper())
    private var mediaPlayer: MediaPlayer? = null
    private var songs:MutableList<Song> = mutableListOf()
    private var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        songsVM = ViewModelProvider(requireActivity())[QuizzViewModel::class.java]

        arguments?.let {
            name = it.getString(ARG_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    private fun createButtonDynamically(song: Song) {
        // creating the button
        val dynamicButton = Button(this.context)

        // setting layout_width and layout_height using layout parameters
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        layoutParams.setMargins(0,10,0,0)
        dynamicButton.layoutParams = layoutParams
        dynamicButton.id = song.id!!
        dynamicButton.text = song.name
        dynamicButton.setBackgroundColor(Color.BLUE)
        dynamicButton.setTextColor(Color.WHITE)
        dynamicButton.setOnClickListener(this)

        // add Button to LinearLayout
        this.view?.findViewById<LinearLayout>(R.id.quiz_btns_container)?.addView(dynamicButton)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTimer = view.findViewById(R.id.tvTimer)
        playGame()
    }

    private fun formatUrl(url: String): String{
        return url.replace("localhost", getString(R.string.host))
    }

    private fun stopAndReleaseSong() {
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release() // Release the resources
        }
        mediaPlayer = null
    }

    private fun playAudio(song: Song){
            try {
                song.file?.let {
                    stopAndReleaseSong()
                    mediaPlayer = MediaPlayer()
                    // Initialize the MediaPlayer with the audio resource
                    mediaPlayer?.setDataSource(formatUrl(it) )
                    // Prepare the MediaPlayer asynchronously
                    mediaPlayer?.prepareAsync()

                    // Set up listener for when the media is prepared
                    mediaPlayer?.setOnPreparedListener {
                        val seconds = mediaPlayer?.duration
                        seconds.let {
                            second ->
                            if (second != null) {
                                if ((second/1000)  < 15) {
                                    opportunities = (second/1000)
                                }
                            }
                        }

                        mediaPlayer?.start() // Start playback when ready
                        startTimer()
                    }
                }

            }catch (e: IOException) {
                e.printStackTrace()
            }
    }

    private fun playGame(){
        if (opportunities > 0){
            clearView()
            songsVM.getSongsQuiz().let { quizzViewModel ->
                songs.clear()
                quizzViewModel.forEach{ song ->
                    songs.add(song)
                    createButtonDynamically(song)
                }
            }

            index = Random.nextInt(songs.count())
            playAudio(songs[index])
        }else{
            stopAndReleaseSong()
            goToResult()
        }
    }

    private fun clearView() {
        val layout = this.view?.findViewById<LinearLayout>(R.id.quiz_btns_container)

        layout?.removeAllViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        handler.removeCallbacksAndMessages(null)
    }

    private fun startTimer() {
        timer = Timer()

        timer.schedule(object : TimerTask() {
            override fun run() {
                // Update the TextView using the handler
                handler.post {
                    tvTimer.text = getString(R.string.timeleft, timeLeft)
                    timeLeft--
                }

                if (timeLeft == 0){
                    resetValues()
                    if (opportunities > 0){
                        handler.post {
                            playGame()
                        }
                    }else{
                        stopAndReleaseSong()
                        goToResult()
                    }
                }
            }
        }, 0, 1000) // Start immediately and repeat every second
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param name Parameter 1.
         * @return A new instance of fragment QuizFragment.
         */
        @JvmStatic
        fun newInstance(name: String ) =
            QuizFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                }
            }
    }

    private fun goToResult(){
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        val fragment = ResultFragment()
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit()
    }

    override fun onClick(view: View?) {

        if (view != null) {
            if (songs[index].id == view.id){
                songsVM.setPoint(20)
                songsVM.goodAnswer()

                if (timeLeft >= 50)
                {
                    songsVM.setPoint(10)
                }
                else if (timeLeft >= 40){
                    songsVM.setPoint(5)
                }
            }
        }
        resetValues()
        playGame()
    }

    private fun resetValues() {
        timer.cancel()
        opportunities--
        timeLeft = 15
    }

    override fun onResume() {
        super.onResume()
        // Override back button behavior
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    resetValues()
                    stopAndReleaseSong()
                }
            })
    }
}