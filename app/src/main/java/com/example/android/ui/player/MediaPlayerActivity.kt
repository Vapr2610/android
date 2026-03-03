package com.example.android.ui.player

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.android.R
import com.example.android.data.AudioTrack
import com.example.android.utils.PermissionHelper
import java.io.File

class MediaPlayerActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var tvCurrentTrack: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var btnPlayPause: Button
    private lateinit var btnStop: Button
    private lateinit var seekBarHandler: Handler

    private var mediaPlayer: MediaPlayer? = null
    private var audioFiles: MutableList<AudioTrack> = mutableListOf()
    private var currentTrackIndex: Int = -1
    private var isPaused = false

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_player)

        initViews()
        seekBarHandler = Handler(Looper.getMainLooper())

        if (PermissionHelper.hasStoragePermissions(this)) {
            loadAudioFiles()
        } else {
            PermissionHelper.requestStoragePermissions(this, PERMISSION_REQUEST_CODE)
        }
    }

    private fun initViews() {
        listView = findViewById(R.id.listViewTracks)
        tvCurrentTrack = findViewById(R.id.tvCurrentTrack)
        seekBar = findViewById(R.id.seekBar)
        btnPlayPause = findViewById(R.id.btnPlayPause)
        btnStop = findViewById(R.id.btnStop)

        btnPlayPause.setOnClickListener { togglePlayPause() }
        btnStop.setOnClickListener { stopPlayback() }

        listView.setOnItemClickListener { _, _, position, _ ->
            playTrack(position)
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mediaPlayer?.seekTo(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun loadAudioFiles() {
        val musicDir = getExternalFilesDir(null) ?: return

        musicDir.listFiles()?.forEach { file ->
            if (!file.isDirectory && file.extension.lowercase() in listOf("mp3", "wav", "ogg", "m4a")) {
                audioFiles.add(AudioTrack(file.name, file.path, file.length()))
            }
        }

        if (audioFiles.isEmpty()) {
            Toast.makeText(this, R.string.player_no_tracks, Toast.LENGTH_SHORT).show()
            return
        }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            audioFiles.map { it.name }
        )
        listView.adapter = adapter
    }

    private fun playTrack(index: Int) {
        if (index !in audioFiles.indices) return

        currentTrackIndex = index
        val track = audioFiles[index]

        try {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer().apply {
                setDataSource(track.path)
                prepare()
                start()
            }

            tvCurrentTrack.text = track.name
            setupSeekBar()
            btnPlayPause.text = getString(R.string.player_pause)
            isPaused = false

        } catch (e: Exception) {
            Toast.makeText(this, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupSeekBar() {
        mediaPlayer?.let { mp ->
            seekBar.max = mp.duration

            seekBarHandler.postDelayed(object : Runnable {
                override fun run() {
                    mediaPlayer?.let {
                        seekBar.progress = it.currentPosition
                        if (it.isPlaying) {
                            seekBarHandler.postDelayed(this, 1000)
                        }
                    }
                }
            }, 1000)
        }
    }

    private fun togglePlayPause() {
        mediaPlayer ?: return

        if (mediaPlayer!!.isPlaying) {
            mediaPlayer?.pause()
            btnPlayPause.text = getString(R.string.player_play)
            isPaused = true
        } else {
            mediaPlayer?.start()
            btnPlayPause.text = getString(R.string.player_pause)
            isPaused = false
            setupSeekBar()
        }
    }

    private fun stopPlayback() {
        mediaPlayer?.stop()
        mediaPlayer?.prepare()
        seekBar.progress = 0
        btnPlayPause.text = getString(R.string.player_play)
        isPaused = false
        seekBarHandler.removeCallbacksAndMessages(null)
    }

    override fun onPause() {
        super.onPause()
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
            isPaused = true
            btnPlayPause.text = getString(R.string.player_play)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        seekBarHandler.removeCallbacksAndMessages(null)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                loadAudioFiles()
            } else {
                Toast.makeText(this, R.string.permission_required, Toast.LENGTH_SHORT).show()
            }
        }
    }
}