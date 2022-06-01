package com.NickRooden.numbergame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.NickRooden.numbergame.R
import com.NickRooden.numbergame.databinding.GameBinding
import com.NickRooden.numbergame.domain.Level
import com.NickRooden.numbergame.domain.ResultsGm
import com.NickRooden.numbergame.domain.Settings

class Game : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().application)
        )[GameViewModel::class.java]
    }

    private lateinit var level : Level

    private var _binding : GameBinding? = null
    private val binding : GameBinding
        get() = _binding ?: throw RuntimeException("GameBinding==null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.opt1.setOnClickListener {
            launchResultFragment(
                ResultsGm(
                    true,
                    10,
                    20,
                    Settings(
                        10,20,30,40
                    )
                )
            )
        }



        viewModel.startGame(level)
        binding.timer.text = "ghjt"
        viewModel.timerStr.observe(viewLifecycleOwner){
            binding.timer.text = it.toString()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun launchResultFragment(result: ResultsGm){

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameResultFrag.newInst(result))
            .addToBackStack(null)
            .commit()

    }

    fun parseArgs(){
        requireArguments().getParcelable<Level>(LEVEL)?.let {
            level = it
        }
    }

    companion object{

        const val LEVEL = "level"
        const val NAME = "game"

        fun newInst(level: Level): Game{
            return Game().apply {
                arguments = Bundle().apply {
                    putParcelable(LEVEL, level)
                }
            }
        }
    }
}