package com.usj.musicquizz.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.usj.musicquizzapi.model.Song
import kotlin.random.Random

class QuizzViewModel: ViewModel() {
    var mutableList:MutableLiveData<MutableList<Song>> = MutableLiveData()
    private lateinit var playerName:String
    private var qualification: Int = 0
    private var qt: Int = 0
    private var answer: Int = 0

    fun getSongsQuiz(quantity: Int = 4): MutableList<Song> {

        qt = quantity
        if (mutableList.value?.count()!! < quantity){
            qt = mutableList.value?.count()!!
        }

        val list: MutableList<Song> = mutableListOf()
        for (i in qt-1 downTo 0){
            val index = mutableList.value?.let { Random.nextInt(1, it.count()) }
            index?.let { mutableList.value?.get(it) }?.let { list.add(it) }
        }

        return list
    }

    fun goodAnswer(){
        if (answer < qt){
            answer++
        }
    }

    fun getAnswer():String{
        return "${answer}/${qt}"
    }

    fun setPoint(point: Int){
        qualification += point
    }

    fun setName(name: String){
        playerName = name
    }

    fun getName():  String = playerName
    fun getQualification():  Int = qualification
}