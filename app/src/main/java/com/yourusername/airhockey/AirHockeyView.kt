package com.yourusername.airhockey

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class AirHockeyView(context: Context) : View(context) {
    private val puckPaint = Paint().apply {
        color = Color.RED
        isAntiAlias = true
    }

    private val paddlePaint = Paint().apply {
        color = Color.BLUE
        isAntiAlias = true
    }

    private val tablePaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    private var puckX = 0f
    private var puckY = 0f
    private var puckSpeedX = 15f
    private var puckSpeedY = 15f
    private val puckRadius = 30f

    private var playerPaddleX = 0f
    private var playerPaddleY = 0f
    private val paddleRadius = 40f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // Initialize positions
        puckX = w / 2f
        puckY = h / 2f
        playerPaddleX = w / 2f
        playerPaddleY = h - 100f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw table
        canvas.drawColor(Color.BLACK)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), tablePaint)
        canvas.drawLine(0f, height/2f, width.toFloat(), height/2f, tablePaint)

        // Draw puck
        canvas.drawCircle(puckX, puckY, puckRadius, puckPaint)

        // Draw player paddle
        canvas.drawCircle(playerPaddleX, playerPaddleY, paddleRadius, paddlePaint)

        // Update puck position
        updatePuck()

        // Request next frame
        invalidate()
    }

    private fun updatePuck() {
        puckX += puckSpeedX
        puckY += puckSpeedY

        // Bounce off walls
        if (puckX - puckRadius < 0 || puckX + puckRadius > width) {
            puckSpeedX = -puckSpeedX
        }
        if (puckY - puckRadius < 0 || puckY + puckRadius > height) {
            puckSpeedY = -puckSpeedY
        }

        // Collision with paddle
        val dx = puckX - playerPaddleX
        val dy = puckY - playerPaddleY
        val distance = Math.sqrt((dx * dx + dy * dy).toDouble())

        if (distance < (puckRadius + paddleRadius)) {
            // Bounce off paddle
            val angle = Math.atan2(dy.toDouble(), dx.toDouble())
            puckSpeedX = (Math.cos(angle) * 15).toFloat()
            puckSpeedY = (Math.sin(angle) * 15).toFloat()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        playerPaddleX = event.x
        playerPaddleY = event.y.coerceIn((height/2).toFloat(), height.toFloat())
        return true
    }
}