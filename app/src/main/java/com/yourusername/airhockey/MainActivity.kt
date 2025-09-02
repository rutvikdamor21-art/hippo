package com.yourusername.airhockey

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.view.WindowManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Keep screen on
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Set the game view
        setContentView(AirHockeyView(this))
    }
}