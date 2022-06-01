package com.NickRooden.numbergame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.NickRooden.numbergame.databinding.ResultFragmentBinding
import com.NickRooden.numbergame.domain.ResultsGm

class GameResultFrag : Fragment() {

    private var _binding : ResultFragmentBinding? = null
    private val binding get() = _binding!!

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