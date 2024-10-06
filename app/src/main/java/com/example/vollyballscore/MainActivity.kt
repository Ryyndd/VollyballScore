package com.example.vollyballscore

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.vollyballscore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {

            if (TextUtils.isEmpty(binding.edTeamA.text.toString()) || TextUtils.isEmpty(binding.edTeamB.text.toString())) {
                Toast.makeText(this, "Masukan Nama Tean Terlebih Dahulu", Toast.LENGTH_SHORT).show()
            } else {
                val bundle = Bundle()
                bundle.putString("TEAM_A" , binding.edTeamA.text.toString().uppercase())
                bundle.putString("TEAM_B" , binding.edTeamB.text.toString().uppercase())
                intent = Intent(this, ScoreActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }

        }

    }
}