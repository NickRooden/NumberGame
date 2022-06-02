package com.NickRooden.numbergame.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.NickRooden.numbergame.R
import com.NickRooden.numbergame.data.RepositoryImpl
import com.NickRooden.numbergame.domain.*

class GameViewModel(application: Application) : AndroidViewModel(application) {

    val repository = RepositoryImpl
    val getQuestionUseCase = GetQuestionUseCase(repository)
    val getSettingsUseCase = GetSettingsUseCase(repository)

    val context = application

    private lateinit var  level : Level
    private lateinit var  settings : Settings

    private var timer: CountDownTimer? = null

    private var _timerStr = MutableLiveData<String>()
    val timerStr : LiveData<String> get() = _timerStr

    private var _question = MutableLiveData<Question>()
    val question : LiveData<Question> get() = _question

    private var _percentOfRightAnswer = MutableLiveData<Int>()
    val percentOfRightAnswer : LiveData<Int> get() = _percentOfRightAnswer

    private var _progressAnswers = MutableLiveData<String>()
    val progressAnswers : LiveData<String> get() = _progressAnswers

    private var _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers: LiveData<Boolean> get() = _enoughCountOfRightAnswers

    private var _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswers: LiveData<Boolean>  get() = _enoughPercentOfRightAnswers

    private var _minPercentOfRightAnswer = MutableLiveData<Int>()
    val minPercentOfRightAnswer : LiveData<Int> get() = _minPercentOfRightAnswer

    private var _gameResult = MutableLiveData<ResultsGm>()
    val gameResult : LiveData<ResultsGm> get() = _gameResult


    private var countOfRightAnswers = 0
    private var countOfQuestions = 0


    fun startGame(level: Level){
        setSettings(level)
        startTimer()
        getQuestion()
        updateProgress()
    }

    fun chooseAnswer(number: Int){
        checkAnswer(number)
        updateProgress()
        getQuestion()
    }

    private fun updateProgress(){
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswer.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            settings.answersToWin
        )
        _enoughCountOfRightAnswers.value = countOfRightAnswers >= settings.answersToWin
        _enoughPercentOfRightAnswers.value = percent >= settings.percentToWin
    }

    private fun calculatePercentOfRightAnswers(): Int{
        if (countOfQuestions == 0){
            return 0
        }
        return ((countOfRightAnswers/countOfQuestions.toDouble())*100).toInt()
    }

    private fun checkAnswer(number: Int){
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer){
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun setSettings(level: Level){
        this.level = level
        this.settings = getSettingsUseCase(level)
        _minPercentOfRightAnswer.value = settings.percentToWin
    }

    private fun getQuestion(){
        _question.value = getQuestionUseCase(settings.maxSum)

    }

    private fun startTimer(){

        timer = object : CountDownTimer(
            settings.timeOfGame * MILLISECOND_IN_SECOND,
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

        _gameResult.value = ResultsGm(
            enoughCountOfRightAnswers.value == true
                    && enoughPercentOfRightAnswers.value == true,
            countOfRightAnswers,
            countOfQuestions,
            settings
        )

    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object{
        const val MILLISECOND_IN_SECOND = 1000L
    }


}