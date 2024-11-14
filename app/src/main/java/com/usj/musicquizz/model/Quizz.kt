package com.usj.musicquizz.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.usj.musicquizzapi.model.Song
import kotlin.random.Random

class QuizzViewModel: ViewModel() {
    var mutableList:MutableLiveData<MutableList<Song>> = MutableLiveData()
    private lateinit var playerName:String
    private var calification: Int = 0

    fun getSongsQuiz(quantity: Int = 3): MutableList<Song> {

        var qt = quantity
        if (mutableList.value?.count()!! < quantity){
            qt = mutableList.value?.count()!!
        }

        val list: MutableList<Song> = mutableListOf<Song>()
        for (i in qt downTo 0){
            val index = mutableList.value?.let { Random.nextInt(1, it.count()) }
            index?.let { mutableList.value?.get(it) }?.let { list.add(it) }
        }

        return list
    }

    fun setPoint(point: Int){
        calification += point
    }

    fun setName(name: String){
        playerName = name
    }

    fun getName():  String = playerName
    fun getCalification():  Int = calification
}