package com.example.myaudioplayer

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class mainMenu : AppCompatActivity() {
    private lateinit var song0 : Button
    private lateinit var song1 : Button
    private lateinit var song2 : Button
    private lateinit var song3 : Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        song0 = findViewById(R.id.song0)
        song0.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_DATA, "0")
            startActivity(intent)
        }

        song1 = findViewById(R.id.song1)
        song1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_DATA, "1")
            startActivity(intent)
        }

        song2 = findViewById(R.id.song2)
        song2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_DATA, "2")
            startActivity(intent)
        }

        song3 = findViewById(R.id.song3)
        song3.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_DATA, "3")
            startActivity(intent)
        }
    }
}