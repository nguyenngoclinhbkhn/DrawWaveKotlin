package com.example.drawwavekotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.otaliastudios.zoom.ZoomLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var zoomLayout: ZoomLayout
    private var x = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        zoomLayout = findViewById(R.id.zoomLayout)
//        zoomLayout.setMinZoom()
//
//        zoomLayout.setMinZoom(2f, ZoomApi.TYPE_ZOOM)
//        zoomLayout.setMaxZoom(4f, ZoomApi.TYPE_ZOOM)
//        horizotal.addView(DrawView(this, null), FrameLayout.LayoutParams.MATCH_PARENT,
//            FrameLayout.LayoutParams.MATCH_PARENT)


        //        setContentView(R.layout.activity_main);
//        btnTest = findViewById(R.id.btnTest);
//        drawViewZoom.post(Runnable {
//            val heightZoom = zoomLayout.layoutParams.height
//            drawViewZoom.layoutParams.height = heightZoom
//            drawViewZoom.requestLayout()
//        })

//        horizotalTopVer1.addView(DrawView(this))

        btnMove.setOnClickListener {
            x -= 100
            zoomLayout.panTo(x, 0f, true)
//            horizotalTop.scrollX = x as Int
            Log.e("TAG", "X $x")
        }
    }
}
