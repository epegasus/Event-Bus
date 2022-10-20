package dev.epegasus.eventbus.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.epegasus.eventbus.databinding.ActivityMainBinding
import dev.epegasus.eventbus.models.Score
import dev.epegasus.eventbus.poster.Calculate
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener { onButtonClick() }
    }

    override fun onResume() {
        super.onResume()
        registerEvent()
    }

    private fun registerEvent() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
            Log.d(TAG, "onStart: Registering")
        } else
            Log.d(TAG, "onStart: Already registered")
    }

    /**
     * if Subscribed one time, it will save subscription history even if app gets killed
     */

    private fun registerEventWithSubscription() {
        if (!EventBus.getDefault().hasSubscriberForEvent(Score::class.java)) {
            Log.d(TAG, "onStart: Subscribing")
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
                Log.d(TAG, "onStart: Registering")
            } else
                Log.d(TAG, "onStart: Already registered")
        } else {
            Log.d(TAG, "onStart: Already Subscribed")
            Toast.makeText(this, "Already Subscribed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onButtonClick() {
        Calculate().calculateResult()
    }

    @Subscribe
    fun onMessageEvent(score: Score) {
        Log.d(TAG, "onMessageEvent: $score")
        binding.textView.text = score.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus().unregister(this)
    }

    companion object {
        const val TAG = "MyTag"
    }
}