package com.NickRooden.numbergame.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.NickRooden.numbergame.R
import com.NickRooden.numbergame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, HomePage())
            .commit()


    }

}