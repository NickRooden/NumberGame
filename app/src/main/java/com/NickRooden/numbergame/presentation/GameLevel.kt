package com.NickRooden.numbergame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.NickRooden.numbergame.R
import com.NickRooden.numbergame.databinding.GameLevelBinding
import com.NickRooden.numbergame.domain.Level

class GameLevel : Fragment() {

    private var _binding : GameLevelBinding? = null
    private val binding : GameLevelBinding
        get() = _binding ?: throw RuntimeException("GameLevelBinding==null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.testButton.setOnClickListener {
            launchGameFragment(Level.TEST)
        }
        binding.easyButton.setOnClickListener {
            launchGameFragment(Level.EASY)
        }
        binding.normalButton.setOnClickListener {
            launchGameFragment(Level.NORMAL)
        }
        binding.hardButton.setOnClickListener {
            launchGameFragment(Level.HARD)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    fun launchGameFragment(level: Level){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, Game.newInst(level))
            .addToBackStack(Game.NAME)
            .commit()

    }

    companion object{

        const val NAME = "game level"

        fun newInst() : GameLevel{
            return GameLevel()
        }
    }

}