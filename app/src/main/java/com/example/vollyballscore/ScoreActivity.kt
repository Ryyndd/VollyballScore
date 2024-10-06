package com.example.vollyballscore

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.vollyballscore.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreBinding

    val viewModel : ScoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModelData = viewModel
        binding.lifecycleOwner = this

        val getName = intent.extras
        viewModel._nameTeamA.value = getName?.getString("TEAM_A")
        viewModel._nameTeamB.value = getName?.getString("TEAM_B")

        binding.tvMatch.text = "LIVE MATCHING"

        liveData()
        buttonAction()
    }



    fun liveData() {

        val result : LiveData<String> = viewModel.ScoreA.map { scoreA ->
            val scoreB = viewModel.ScoreB.value ?: 0

            val nameA = viewModel._nameTeamA.value
            val nameB = viewModel._nameTeamB.value
            when {
                scoreA >= 25 && scoreA - scoreB >= 2 -> "$nameA WINS"
                scoreA >= 24 && scoreB >= 24 -> "DEUCE"
                else -> "LIVE MATCHING"
            }
        }

        result.observe(this) {
                resultMessage -> binding.tvMatch.text = resultMessage
            if (resultMessage.contains("WINS"))
            {
                disableButton(active = false)
            } else {
                disableButton(active = true)
            }
        }

        val resultB : LiveData<String> = viewModel.ScoreB.map { scoreB ->
            val scoreA = viewModel.ScoreA.value ?: 0
            val nameA = viewModel._nameTeamA.value
            val nameB = viewModel._nameTeamB.value
            when {
                scoreB >= 25 && scoreB - scoreA >= 2 -> "$nameB WINS"
                scoreA >= 24 && scoreB >= 24 -> "DEUCE"
                else -> "LIVE MATCHING"
            }
        }

        resultB.observe(this) {
                resultMessage -> binding.tvMatch.text = resultMessage
            if (resultMessage.contains("WINS"))
            {
                disableButton(active = false)
            } else {
                disableButton(active = true)
            }
        }
    }


    fun disableButton( active : Boolean ) {
        binding.btnPlusA.isEnabled = active
        binding.btnPlusB.isEnabled = active

        if ( active == false){
            binding.btnPlusA.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            binding.btnPlusB.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        }
    }


    fun buttonAction() {

        binding.btnBack.setOnClickListener {
            val back = Intent(this, MainActivity::class.java)
            startActivity(back)
            finish()
        }

        binding.btnPlusA.setOnClickListener {
            viewModel.incrementScore(isScoreA = true)
        }

        binding.btnPlusB.setOnClickListener {
            viewModel.incrementScore(isScoreA = false)
        }

        binding.btnReset.setOnClickListener {
            viewModel.resetScore()
            disableButton(active = true)
            binding.btnPlusA.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            binding.btnPlusB.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        }
    }

}