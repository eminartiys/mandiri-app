package com.mandiri.application.feature.trailer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.mandiri.application.Keys
import com.mandiri.application.databinding.ActivityTrailerBinding


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class TrailerActivity : YouTubeBaseActivity() {

    private lateinit var binding: ActivityTrailerBinding
    private var movieHash = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrailerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData(intent.extras)
        configurePlayer()
    }

    private fun getIntentData(extras: Bundle?) {
        movieHash = extras?.getString(EXTRAS_VIDEO_HASH).orEmpty()
    }

    private fun configurePlayer() {
        binding.uiViewYoutubePlayer.initialize(
            Keys.getYoutubeApiKey(),
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider?,
                    player: YouTubePlayer?,
                    p2: Boolean
                ) {
                    player?.loadVideo(movieHash)
                    player?.play()
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider?,
                    initializationResult: YouTubeInitializationResult?
                ) {
                    Toast.makeText(this@TrailerActivity, "Video player Failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    companion object {
        const val EXTRAS_VIDEO_HASH = "EXTRAS_VIDEO_HASH"

        @JvmStatic
        fun startActivity(context: Context?, videoHash: String) {
            val intent = Intent(context, TrailerActivity::class.java)
            intent.putExtras(bundleOf().apply {
                putString(EXTRAS_VIDEO_HASH, videoHash)
            })
            context?.startActivity(intent)
        }
    }
}