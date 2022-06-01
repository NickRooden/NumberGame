package com.NickRooden.numbergame.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.NickRooden.numbergame.data.RepositoryImpl
import com.NickRooden.numbergame.domain.GetQuestionUseCase
import com.NickRooden.numbergame.domain.GetSettingsUseCase
import com.NickRooden.numbergame.domain.Level
import com.NickRooden.numbergame.domain.Settings

class GameViewModel(application: Application) : AndroidViewModel(application) {

    val repository = RepositoryImpl
    val getQuestionUseCase = GetQuestionUseCase(repository)
    val getSettingsUseCase = GetSettingsUseCase(repository)

    private lateinit var  level : Level
    private lateinit var  settings : Settings

    private var timer: CountDownTimer? = null

    private var _timerStr = MutableLiveData<String>()
    val timerStr : LiveData<String> get() = _timerStr

    fun startGame(level: Level){
        setSettings(level)
        startTimer(settings.timeOfGame)
    }

    private fun setSettings(level: Level){
        this.level = level
        this.settings = getSettingsUseCase(level)
    }



    private fun startTimer(seconds: Int){

        timer = object : CountDownTimer(
            seconds * MILLISECOND_IN_SECOND,
            MILLISECOND_IN_SECOND
        ){
            override fun onTick(millisUntilFinished: Long) {
                _timerStr.value = formatTimer(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }


        }
        timer?.start()

    }
    private fun formatTimer(millisUntilFinished: Long): String{
        val second = millisUntilFinished / MILLISECOND_IN_SECOND
        val minutes = second / 60
        val lastSecond = second - minutes * 60
        return String.format("%02d:%02d", minutes, lastSecond)
    }

    fun finishGame(){

    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object{
        const val MILLISECOND_IN_SECOND = 1000L
    }


}