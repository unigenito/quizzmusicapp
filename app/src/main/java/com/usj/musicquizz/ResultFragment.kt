package com.usj.musicquizz

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.usj.musicquizz.model.QuizzViewModel


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class ResultFragment : Fragment(), View.OnClickListener {
    private lateinit var songsVM: QuizzViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        songsVM = ViewModelProvider(requireActivity())[QuizzViewModel::class.java]

        songsVM.getQualification().toString()
            .also { view?.findViewById<TextView>(R.id.txtPoint)?.text = it }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.txtAnswer)?.text = songsVM.getAnswer()
        view.findViewById<TextView>(R.id.txtPlayer)?.text = songsVM.getName()
        view.findViewById<TextView>(R.id.txtPoint)?.text = "${songsVM.getQualification()}"

        view.findViewById<Button>(R.id.btnFinish)?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        val fragment = HomeFragment()
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit()
    }

    override fun onResume() {
        super.onResume()
        // Override back button behavior
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Show a confirmation dialog or other custom behavior
                    AlertDialog.Builder(context)
                        .setMessage("Are you sure you want to go back?")
                        .setPositiveButton("Yes") { _, _ ->
                            val transaction =
                                requireActivity().supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.fragment_container, HomeFragment())
                            transaction.commit()
                        }
                        .setNegativeButton("No", null)
                        .show()
                }
            })
    }

}