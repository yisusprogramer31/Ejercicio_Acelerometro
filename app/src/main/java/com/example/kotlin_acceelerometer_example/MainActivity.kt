// APLICACION NIVEL BURBUJA - MEI
// ALUMNO: MARESCA, JESUS MARIA
package com.example.kotlin_acceelerometer_example

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var bubbleView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bubbleView = findViewById(R.id.bubbleView)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0]
            val y = it.values[1]

            // Map the accelerometer values to screen coordinates
            val parentView = bubbleView.parent as View
            val centerX = parentView.width / 2
            val centerY = parentView.height / 2

            val bubbleX = centerX - x * 50 // Adjust sensitivity as needed
            val bubbleY = centerY + y * 50

            bubbleView.x = bubbleX.toFloat()
            bubbleView.y = bubbleY.toFloat()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // We don't need to handle accuracy changes for this app
    }
}
