package com.example.drawwavekotlin

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView

class DrawView : ImageView {

    private var paintTab: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var path: Path
    private val DISTANCE = 8
    private var x: Float? = null
    private var y: Float? = null
    private lateinit var bitmap: Bitmap
    private val ANGLE = 45
    private val startY = 100f
    private val DISTANCE_DISTANCE = 150
    private var canvas: Canvas? = null
    private lateinit var list: ArrayList<PointCalendar>
    private lateinit var bottomRightList: ArrayList<PointCalendar>
    private var bitmapMonth : Bitmap ?= null
    private val widthBitmap = 100;
    private val heightBitmap = 74

    constructor(context: Context) : this(context, null) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        paintTab.color = Color.RED
        paintTab.strokeWidth = 25f
        paintTab.style = Paint.Style.STROKE
        paintTab.isDither = true // set the dither to true
        paintTab.strokeJoin = Paint.Join.ROUND // set the join to round you want
        paintTab.strokeCap = Paint.Cap.ROUND // set the paint cap to round too
        paintTab.pathEffect = CornerPathEffect(20f)
        path = Path()
        bitmapMonth = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.month),
           widthBitmap, heightBitmap, true );
//        canvas = Canvas()
        x = 50f
        y = 400f
        list = ArrayList<PointCalendar>()
        bottomRightList = ArrayList<PointCalendar>()
        createListPoint()
    }

    init {
        init()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = 50000
        val height =
            MeasureSpec.getSize(heightMeasureSpec) // Since 3000 is bottom of last Rect to be drawn added and 50 for padding.
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        canvas!!.drawBitmap(bitmap, 0f, 0f, paintTab)
        Log.e("TAG", "draw")
        drawLine(canvas)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        Log.e("TAG", "dispatch draw")
    }

    private fun drawLine(canvas: Canvas?) {
        path!!.reset()
        path!!.moveTo(50f, startY.toFloat())
        //        path.lineTo(x, y);
        var countDraw = 1
        var xBottomCornerLeft = x!!
        var yBottomCornerLeft = y!!
        var xBitmap = 0f;

        //draw vertical down line
        while (countDraw <= 50) {
//            if (countDraw == 50) break
            canvas?.drawBitmap(bitmapMonth!!, xBitmap, 0f,paintTab)
            Log.e("TAG", "Drawline ")
            path!!.lineTo(xBottomCornerLeft, yBottomCornerLeft)
            //draw corner bottom left
            for (p in list) {
                path!!.lineTo(xBottomCornerLeft + p.x, yBottomCornerLeft + p.y)
            }
            //draw horizontal bottom line
            val bottomLeft: Float = xBottomCornerLeft + list[list.size - 1].x
            path!!.lineTo(
                bottomLeft + DISTANCE_DISTANCE,
                y!! + list.get(list.size - 1).y
            )
            //draw corner bottom right
            for (i in bottomRightList.indices) {
                path!!.lineTo(
                    1 * (bottomLeft + DISTANCE_DISTANCE + bottomRightList[i].x),
                    1 * (y!! + list.get(list.size - 1).y - bottomRightList[i].y)
                )
            }

            // draw vertical up line
            val xBottomRight: Float =
                bottomLeft + DISTANCE_DISTANCE + bottomRightList[bottomRightList.size - 1].x
            path!!.lineTo(
                1 * (bottomLeft + DISTANCE_DISTANCE + bottomRightList.get(
                    bottomRightList.size - 1
                ).x), 180f
            )
            xBitmap = bottomLeft + DISTANCE_DISTANCE + bottomRightList[bottomRightList.size - 1].x / 2
            canvas?.drawBitmap(bitmapMonth!!, xBitmap, 0f,paintTab)

            //draw corner top left
            for (i in list.indices) {
                path!!.lineTo(
                    1 * (xBottomRight + list[i].x),
                    180 - list.get(i).y
                )
            }
            //draw horizontal top line
            val xTopLeft: Float =
                xBottomRight + list.get(list.size - 1).x + DISTANCE_DISTANCE
            path!!.lineTo(1 * xTopLeft, 100f)
            //draw corner top right
            for (i in bottomRightList.indices) {
                path!!.lineTo(
                    1 * (xTopLeft + bottomRightList[i].x),
                    100 + bottomRightList[i].y
                )
            }
            countDraw++
            xBottomCornerLeft = xTopLeft + bottomRightList[bottomRightList.size - 1].x
            yBottomCornerLeft = y!!
            xBitmap = xTopLeft + bottomRightList[bottomRightList.size - 1].x / 2
            //        path.lineTo(xBottomCornerLeft, yBottomCornerLeft);
        }
        canvas!!.drawPath(path!!, paintTab)
    }


    private fun createListPoint() {
        var countBottom = 1
        for (i in 10 downTo 1) { //tính toán các điểm với toạ độ x từ bé đến lớn ở góc dưới bên trái, và toạ độ y từ bé đến lớn
            val v: Double =
                DISTANCE * countBottom * Math.tan(Math.PI * (ANGLE / i) / 180)
            list.add(
                PointCalendar(
                    v.toFloat(),
                    DISTANCE * countBottom.toFloat()
                )
            )
            //            tính toá các điểm với toạ độ x từ bé đến lớn ở góc dưới bên phải, và toạ độ y từ bé đến lớn
            bottomRightList.add(
                PointCalendar(
                    DISTANCE * countBottom.toFloat(),
                    v.toFloat()
                )
            )
            countBottom++
        }
    }
}
