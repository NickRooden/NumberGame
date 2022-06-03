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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.NickRooden.numbergame.R
import com.NickRooden.numbergame.databinding.ResultFragmentBinding
import com.NickRooden.numbergame.domain.ResultsGm

class GameResultFrag : Fragment() {

    private var _binding : ResultFragmentBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<GameResultFragArgs>()

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

        binding.resultImage.setImageResource(getFinishImage())
        binding.winner.text = getWinnerLoser()
        binding.answersToWin.text = String.format(
            getString(R.string.answers_to_win),
            args.result.settings.answersToWin
        )
        binding.rightAnswers.text = String.format(
            getString(R.string.right_answers),
            args.result.rightAnswers
        )
        binding.persentToWin.text = String.format(
            getString(R.string.percent_to_win),
            args.result.settings.percentToWin
        )
        binding.rightPercent.text = String.format(
            getString(R.string.right_percent),
            rightPercent()
        )

    }

    private fun rightPercent(): String {
        return if (args.result.rightAnswers == 0){
            "0"
        }else{
            ((args.result.rightAnswers / args.result.allAnswers.toDouble()) * 100)
                .toString()
        }
    }

    private fun getWinnerLoser(): String {
        return if (args.result.winner){
            getString(R.string.winner)
        }else{
            getString(R.string.loser)
        }
    }

    private fun getFinishImage() :  Int{
        return if (args.result.winner){
            R.drawable.giftbox
        }else{
            R.drawable.closed
        }
    }

    fun retryGame(){
        findNavController().popBackStack()
    }

}