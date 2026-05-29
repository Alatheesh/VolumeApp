package com.lathu.volume

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var audioManager: AudioManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val btnUp = findViewById<Button>(R.id.btnVolumeUp)
        val btnDown = findViewById<Button>(R.id.btnVolumeDown)

        btnUp.setOnClickListener { changeVolume(AudioManager.ADJUST_RAISE) }
        btnDown.setOnClickListener { changeVolume(AudioManager.ADJUST_LOWER) }
    }

    private fun changeVolume(direction: Int) {
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, direction, 0)
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        Toast.makeText(this, "Volume: $currentVolume", Toast.LENGTH_SHORT).show()
    }
}
