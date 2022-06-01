package com.NickRooden.numbergame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.NickRooden.numbergame.R
import com.NickRooden.numbergame.databinding.HomePageBinding

class HomePage: Fragment() {

    private var _binding : HomePageBinding? = null
    private val binding : HomePageBinding
        get() = _binding ?: throw RuntimeException("HomePageBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startButton.setOnClickListener {

            launchGameLevelFragment()

        }
    }

    fun launchGameLevelFragment(){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameLevel.newInst())
            .addToBackStack(GameLevel.NAME)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}