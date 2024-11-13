package com.usj.musicquizz

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.usj.musicquizz.model.QuizzViewModel
import com.usj.musicquizzapi.model.Song

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_NAME = "name"
private const val ARG_SONGS = "songs"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizFragment : Fragment() {

    private var name: String? = null
    private lateinit var songs: QuizzViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        songs = ViewModelProvider(requireActivity())[QuizzViewModel::class.java]
        arguments?.let {
            name = it.getString(ARG_NAME)
            //songs = it.getParcelableArray(ARG_PARAM2, MutableList<Song>)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    private fun createButtonDynamically(view: View, song: Song) {
        // creating the button
        val dynamicButton = Button(view.context)

        // setting layout_width and layout_height using layout parameters
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        layoutParams.setMargins(0,6,0,0)
        dynamicButton.layoutParams = layoutParams
        dynamicButton.id = song.id!!
        dynamicButton.text = song.name
        dynamicButton.setBackgroundColor(Color.BLUE)
        dynamicButton.setTextColor(Color.WHITE)
        // add Button to LinearLayout
        view.findViewById<LinearLayout>(R.id.quiz_btns_container)?.addView(dynamicButton)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        songs.getSongsQuiz().let { quizzViewModel ->
            quizzViewModel.forEach{ song ->
                print(song)
                createButtonDynamically(view, song)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param name Parameter 1.
         * @param songs Parameter 2.
         * @return A new instance of fragment QuizFragment.
         */
        @JvmStatic
        fun newInstance(name: String ) =
            QuizFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                    //putParcelableArray(ARG_SONGS, songs)
                }
            }
    }
}