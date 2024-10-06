package com.example.vollyballscore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel : ViewModel() {

    private val _ScoreA = MutableLiveData<Int>(0)
    val ScoreA : LiveData<Int> get() = _ScoreA

    private  val _ScoreB = MutableLiveData<Int>(0)
    val ScoreB : LiveData<Int> get() = _ScoreB

    val _nameTeamA = MutableLiveData<String>()
    val _nameTeamB = MutableLiveData<String>()

    fun incrementScore( isScoreA : Boolean) {
        if ( isScoreA) {
            _ScoreA.value = _ScoreA.value !! + 1
        } else {
            _ScoreB.value = _ScoreB.value !! + 1
        }
    }

    fun resetScore(){
        _ScoreA.value = 0
        _ScoreB.value = 0
    }

}