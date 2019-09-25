package com.example.qrdetect

import android.content.Context
import android.graphics.Matrix
import android.graphics.PixelFormat
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.kroegerama.kaiteki.bcode.BarcodeResultListener
import com.kroegerama.kaiteki.bcode.ui.BarcodeFragment
import kotlin.math.acos
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.math.sqrt


class MainActivity : AppCompatActivity(), BarcodeResultListener, SensorEventListener{

    private lateinit var mSensorManager: SensorManager
    private var mSensors: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensors = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mSensorManager.registerListener(this, mSensors, SensorManager.SENSOR_DELAY_NORMAL)

        //connect  GLSurfaceView with layout
        val glView = findViewById<View>(R.id.gl_surface) as GLSurfaceView
        glView.setEGLContextClientVersion(3)
        glView.setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        glView.setRenderer(arRenderer())
        glView.setZOrderOnTop(true)
        glView.getHolder().setFormat(PixelFormat.RGBA_8888)
        glView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY)

        val barcodeFragment = BarcodeFragment.makeInstance(
            formats = listOf(BarcodeFormat.QR_CODE),
            barcodeInverted = false
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, barcodeFragment)
            .commit()
        barcodeFragment

    }

    override fun onBarcodeResult(result: Result): Boolean {
        resultPoints[0] = result.resultPoints[0].x
        resultPoints[1] = result.resultPoints[0].y
        resultPoints[2] = result.resultPoints[1].x
        resultPoints[3] = result.resultPoints[1].y
        resultPoints[4] = result.resultPoints[2].x
        resultPoints[5] = result.resultPoints[2].y
        resultPoints[6] = result.resultPoints[3].x
        resultPoints[7] = result.resultPoints[3].y
        string_val = result.toString()

        return false
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onSensorChanged(p0: SensorEvent?) {
        when (p0?.sensor?.type){
            Sensor.TYPE_ACCELEROMETER -> {
                var angleX = p0.values[0]
                var angleY = p0.values[1]
                var angleZ = p0.values[2]

                val normOfG = sqrt(angleX * angleX + angleY * angleY + angleZ * angleZ)

                angleZ /= normOfG

                inclinationZ = Math.toDegrees(acos(angleZ).toDouble()).roundToInt()
            }
        }
    }


    companion object{
        var resultPoints = FloatArray(8)
        var string_val = ""
        var inclinationZ = 0
    }
}