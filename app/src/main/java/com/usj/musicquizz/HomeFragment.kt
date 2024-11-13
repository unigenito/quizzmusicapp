package com.usj.musicquizz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.usj.musicquizzapi.model.Song

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
interface AnswersListener {
    fun onSelected(questionId: Int, name: String)
}

class HomeFragment : Fragment(), View.OnClickListener {
    private lateinit var answersListener: AnswersListener
    private var teName:EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AnswersListener) {
            answersListener = context
        } else {
            throw RuntimeException("Must implement AnswersListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teName = view.findViewById(R.id.teName)
        view.findViewById<Button>(R.id.btnStart).setOnClickListener(this)
        view.findViewById<Button>(R.id.btnContinue).setOnClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param song Parameter 1.
         * @return A new instance of fragment HomeFragment.
         */

        @JvmStatic
        fun newInstance(songs: MutableList<Song>) =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onClick(v: View?) {

        v?.let { question ->

            if (teName?.text.toString().isNotEmpty()){
                answersListener.onSelected(question.id,
                    teName?.text.toString()
                )
            }else{
                Toast.makeText(this.context, "Please, introduce your name", Toast.LENGTH_LONG)
                    .show()
            }

        }
    }
}