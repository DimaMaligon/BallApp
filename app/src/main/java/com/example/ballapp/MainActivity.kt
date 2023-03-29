package com.example.ballapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.ballapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var timer : CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        animationButton()
        animationText()
    }

    private fun getDesire(): String {
        return resources.getStringArray(R.array.desires)[randomNumber()]
    }

    private fun randomNumber(): Int {
        val size = resources.getStringArray(R.array.desires).size - 1
        return (0..size).random()
    }

    private fun animationText(timerSeconds: Long = 3) {
        binding.apply {
            button.setOnClickListener {
              timer?.cancel()
              timer =  object : CountDownTimer((timerSeconds * 1000), 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        textDesire.visibility = View.INVISIBLE
                    }

                    override fun onFinish() {
                        textDesire.animate().apply {
                            duration = 1000
                            rotationBy(360f)
                            textDesire.visibility =View.VISIBLE
                            textDesire.text = getDesire()
                        }.start()
                    }

                }.start()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun animationButton() {
        var enlarge = AnimationUtils.loadAnimation(this, R.anim.enlarge)
        var shrink = AnimationUtils.loadAnimation(this, R.anim.shrink)
        binding.apply {
            button.setOnTouchListener { v, event ->
                val action = event.action
                when (action) {

                    MotionEvent.ACTION_DOWN -> {
                        button.startAnimation(enlarge)
                    }
                    MotionEvent.ACTION_MOVE -> {}

                    MotionEvent.ACTION_UP -> {
                        button.startAnimation(shrink)
                    }

                    MotionEvent.ACTION_CANCEL -> {

                    }

                    else -> {

                    }
                }
                false
            }
        }
    }
}