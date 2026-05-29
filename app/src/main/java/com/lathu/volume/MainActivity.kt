package com.lathu.volume

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var audioManager: AudioManager
    private lateinit var volumeSeekBar: SeekBar
    private lateinit var tvVolumeText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Connect to the phone's audio hardware
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // Find all our UI pieces
        volumeSeekBar = findViewById(R.id.volumeSeekBar)
        tvVolumeText = findViewById(R.id.tvVolumeText)
        val btnUp = findViewById<Button>(R.id.btnVolumeUp)
        val btnDown = findViewById<Button>(R.id.btnVolumeDown)

        // 1. Setup the slider when the app first opens
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        
        volumeSeekBar.max = maxVolume
        volumeSeekBar.progress = currentVolume
        tvVolumeText.text = "Volume: $currentVolume"

        // 2. Make the slider change the volume when dragged
        volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) { // Only change hardware volume if the user physically dragged it
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
                    tvVolumeText.text = "Volume: $progress"
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // 3. Make the buttons sync with the slider
        btnUp.setOnClickListener { changeVolume(AudioManager.ADJUST_RAISE) }
        btnDown.setOnClickListener { changeVolume(AudioManager.ADJUST_LOWER) }
    }

    private fun changeVolume(direction: Int) {
        // Change the actual hardware volume
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, direction, 0)
        
        // Get the new volume number
        val newVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        
        // Update the visual slider and text label so they move instantly!
        volumeSeekBar.progress = newVolume
        tvVolumeText.text = "Volume: $newVolume"
    }
}
