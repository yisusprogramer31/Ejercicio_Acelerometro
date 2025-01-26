package com.example.kotlin_acceelerometer_example

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    private lateinit var xValue: TextView
    private lateinit var yValue: TextView
    private lateinit var zValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vincular los TextViews de la UI
        xValue = findViewById(R.id.x_value)
        yValue = findViewById(R.id.y_value)
        zValue = findViewById(R.id.z_value)

        // Configurar el SensorManager y el acelerómetro
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (accelerometer == null) {
            Toast.makeText(this, "Acelerómetro no disponible", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        // Registrar el listener del sensor
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        // Desregistrar el listener del sensor
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0]
            val y = it.values[1]
            val z = it.values[2]

            // Actualizar los valores en la UI
            xValue.text = "X: $x"
            yValue.text = "Y: $y"
            zValue.text = "Z: $z"

            // Detectar un giro rápido
            val accelerationMagnitude = Math.sqrt((x * x + y * y + z * z).toDouble())
            if (accelerationMagnitude > 30) { // Umbral de aceleración rápida
                Toast.makeText(this, "¡Giro rápido detectado!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}