package com.example.myaudioplayer

import android.media.MediaPlayer
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myaudioplayer.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var isPlaying: Boolean = false
    var player: MediaPlayer? = null
    private lateinit var seekBar: SeekBar
    private lateinit var runnable: Runnable
    private var handler = Handler()
    private lateinit var home : ImageButton

    private lateinit var judul : TextView
    private lateinit var waktu : TextView
    companion object {
        const val EXTRA_DATA = "0"
    }

    var songs = arrayOf(
        R.raw.song,
        R.raw.dancinginthedark,
        R.raw.dusktilldawn,
        R.raw.muffin
    )
    var title = arrayOf(
        "Kun Anta",
        "Joji - Dancing in the dark",
        "Zayn - Dusk Till Dawn",
        "Muffin Song"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var songData = intent.getStringExtra(EXTRA_DATA)?.toInt()

        judul = findViewById(R.id.judul)
        judul.text = title[songData!!]

        home = findViewById(R.id.home)
        home.setOnClickListener {
            val intent = Intent(this, mainMenu::class.java)
            startActivity(intent)
        }

        seekBar = findViewById(R.id.seekBar)
        waktu = findViewById(R.id.waktu)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    player!!.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Do something when touch starts, if needed
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Do something when touch stops, if needed
            }
        })

        binding.fabPlayOrPause.setOnClickListener {
            if (isPlaying) {
                if (player != null) {
                    player!!.pause()
                    isPlaying = false
                    binding.fabPlayOrPause.setImageResource(R.drawable.ic_play)
                }
            } else {
                if (player == null) {
                    player = MediaPlayer.create(this, songs[songData!!])
                }
                player!!.start()
                isPlaying = true

                initializeSeekBar()
                with(binding) {
                    fabPlayOrPause.setImageResource(R.drawable.ic_pause)
                    fabStop.visibility = View.VISIBLE
                }

            }
        }

        binding.fabStop.setOnClickListener {
            seekBar.progress = 0
            waktu.text = "00:00"
            stopPlayer()
            binding.fabStop.visibility = View.GONE
        }

        player?.setOnCompletionListener {
            stopPlayer()
            seekBar.progress = 0
            waktu.text = "00:00"
        }

    }

    private fun stopPlayer() {
        if (player != null) {
            player!!.release()
            player = null
        }
        isPlaying = false
        seekBar.progress = 0
        binding.fabPlayOrPause.setImageResource(R.drawable.ic_play)
    }

    override fun onStop() {
        super.onStop()
        stopPlayer()
    }

    private fun initializeSeekBar() {
        seekBar.max = player!!.duration

        runnable = Runnable {
            if(player != null){
                seekBar.progress = player!!.currentPosition

                val currentPosition = player!!.currentPosition / 1000
                val minutes = currentPosition / 60
                val seconds = currentPosition % 60
                waktu.text = String.format("%02d:%02d", minutes, seconds)
            }

            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }
}