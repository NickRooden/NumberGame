package com.NickRooden.numbergame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.NickRooden.numbergame.R
import com.NickRooden.numbergame.databinding.ResultFragmentBinding
import com.NickRooden.numbergame.domain.ResultsGm

class GameResultFrag : Fragment() {

    private var _binding : ResultFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().application)
        )[GameViewModel::class.java]
    }

    private lateinit var result: ResultsGm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ResultFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAgain.setOnClickListener {
            retryGame()
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    retryGame()
                }

            }
        )

        binding.resultImage.setImageResource(getFinishImage())
        binding.winner.text = getWinnerLoser()
        binding.answersToWin.text = String.format(
            getString(R.string.answers_to_win),
            result.settings.answersToWin
        )
        binding.rightAnswers.text = String.format(
            getString(R.string.right_answers),
            result.rightAnswers
        )
        binding.persentToWin.text = String.format(
            getString(R.string.percent_to_win),
            result.settings.percentToWin
        )
        binding.rightPercent.text = String.format(
            getString(R.string.right_percent),
            rightPercent()
        )



    }

    private fun rightPercent(): String {
        if (result.rightAnswers == 0){
            return "0"
        }else{
            return ((result.rightAnswers / result.allAnswers.toDouble()) * 100).toString()
        }

    }

    private fun getWinnerLoser(): String {
        return if (result.winner){
            getString(R.string.winner)
        }else{
            getString(R.string.loser)
        }
    }

    private fun getFinishImage() :  Int{
        return if (result.winner){
            R.drawable.giftbox
        }else{
            R.drawable.closed
        }

    }

    fun parseArgs(){
        requireArguments().getParcelable<ResultsGm>(GAME_RESULT)?.let {
            result = it
        }
    }

    fun retryGame(){
        requireActivity().supportFragmentManager
            .popBackStack(Game.NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    companion object{

        const val GAME_RESULT = "game_result"

        fun newInst(result: ResultsGm): GameResultFrag{

            return GameResultFrag().apply {
                arguments = Bundle().apply {
                    putParcelable(GAME_RESULT, result)
                }
            }
        }
    }
}