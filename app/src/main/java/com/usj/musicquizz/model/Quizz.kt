package com.usj.musicquizz.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.usj.musicquizzapi.model.Song
import kotlin.random.Random

class QuizzViewModel: ViewModel() {
    var mutableList:MutableLiveData<MutableList<Song>> = MutableLiveData()

    fun getSongsQuiz(quantity: Int = 4): MutableList<Song> {

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
}