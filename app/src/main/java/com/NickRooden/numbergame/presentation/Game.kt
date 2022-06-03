package com.NickRooden.numbergame.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.NickRooden.numbergame.R
import com.NickRooden.numbergame.databinding.GameBinding
import com.NickRooden.numbergame.domain.Level
import com.NickRooden.numbergame.domain.ResultsGm
import com.NickRooden.numbergame.domain.Settings

class Game : Fragment() {

    private val args by navArgs<GameArgs>()

    private val viewModelFactory by lazy {
        GameViewModelFactory(requireActivity().application, args.level)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }
    private val optins by lazy {
        mutableListOf<TextView>().apply {
            add(binding.opt1)
            add(binding.opt2)
            add(binding.opt3)
            add(binding.opt4)
            add(binding.opt5)
            add(binding.opt6)
        }
    }

    private lateinit var level : Level

    private var _binding : GameBinding? = null
    private val binding : GameBinding
        get() = _binding ?: throw RuntimeException("GameBinding==null")


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
        observeViewModel()
        setClickListenerOnOptions()
    }

    private fun setClickListenerOnOptions(){
        for (opt in optins){
            opt.setOnClickListener {
                viewModel.chooseAnswer(opt.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() {
        viewModel.timerStr.observe(viewLifecycleOwner) {
            binding.timer.text = it.toString()
        }

        viewModel.question.observe(viewLifecycleOwner) {
            binding.sum.text = it.sum.toString()
            binding.visibleNbr.text = it.numberToSee.toString()
            for (i in 0 until optins.size) {
                optins[i].text = it.nmbrToChoice[i].toString()
            }
        }

        viewModel.percentOfRightAnswer.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressText.setTextColor(getColorByState(it))
        }
        viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        viewModel.minPercentOfRightAnswer.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchResultFragment(it)
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner){
            binding.progressText.text = it
        }
    }

    private fun getColorByState(state: Boolean): Int{
        val colorToBar = if (state){
            android.R.color.holo_green_light
        }else{
            android.R.color.holo_red_light
        }
       return ContextCompat.getColor(requireContext(), colorToBar)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun launchResultFragment(result: ResultsGm){

        findNavController().navigate(GameDirections.actionGameToGameResultFrag(result))

    }

}