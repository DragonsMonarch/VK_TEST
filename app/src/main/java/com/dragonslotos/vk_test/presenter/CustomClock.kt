package com.dragonslotos.vk_test.presenter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.dragonslotos.vk_test.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Calendar
import kotlin.math.min


class CustomClock @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
): View(context, attrs, defStyleAttr, defStyleRes) {

    //paramters
    private var radius = 0f;
    private var contur = 25f;
    //mark_para,meters
    private var special_mark_length = 100f;
    private var mark_length = 50f
    private var mark_width = 10f
    //pointers width
    private var pointer_hour_width = 25f
    private var pointer_minute_width = 15f
    private var pointer_seconds_width = 10f

    //Color parameters
    //dial color
    private var conturColor: Int = Color.BLACK
    private var dial: Drawable = ColorDrawable(Color.WHITE)
    //numerical color
    private var number_color: Int = Color.BLACK
    //Marks Color
    private var mark_color: Int = Color.BLACK
    private var special_mark_color: Int = Color.BLACK
    //Pointers Color
    private var pointer_hour_color: Int = Color.BLACK
    private var pointer_minute_color: Int = Color.BLACK
    private var pointer_seconds_color: Int = Color.RED

    private var numerical: Boolean = true

    //Time
    private var Hour:Float = 0f
    private var Minute:Float = 0f
    private var Seconds:Float = 0f

    private val mCirclePaint = Paint().apply {
        style = Paint.Style.FILL
    }
    private val markPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
        strokeWidth = mark_width
    }

    private val numPaint =  Paint().apply {
        color = number_color
        strokeWidth = mark_width
    }

    private val timePointer = Paint().apply {
        color = pointer_hour_color
        strokeWidth = pointer_hour_width
    }
    init {

        val calendar: Calendar = Calendar.getInstance()
        val typedArray = context.obtainStyledAttributes(attrs,
            R.styleable.CustomClock, defStyleAttr, 0)
        conturColor = typedArray.getColor(R.styleable.CustomClock_conturColor, Color.BLACK)
        number_color = typedArray.getColor(R.styleable.CustomClock_numberColor, Color.BLACK)
        mark_color = typedArray.getColor(R.styleable.CustomClock_markColor, Color.BLACK)
        special_mark_color = typedArray.getColor(R.styleable.CustomClock_specialMarkColor, Color.BLACK)
        pointer_seconds_color = typedArray.getColor(R.styleable.CustomClock_secondsColor, Color.RED)
        pointer_minute_color = typedArray.getColor(R.styleable.CustomClock_minuteColor, Color.BLACK)
        pointer_hour_color = typedArray.getColor(R.styleable.CustomClock_hourColor, Color.BLACK)
        setDialBackground(typedArray.getColor(R.styleable.CustomClock_dialColor, Color.WHITE))
        val drawable: Drawable? = typedArray.getDrawable(R.styleable.CustomClock_dialImage)
        if(!(drawable == null)){
            dial = drawable
        }
        Hour = typedArray.getFloat(R.styleable.CustomClock_hour, calendar.get(Calendar.HOUR).toFloat())
        Minute = typedArray.getFloat(R.styleable.CustomClock_minute, calendar.get(Calendar.MINUTE).toFloat())
        Seconds = typedArray.getFloat(R.styleable.CustomClock_seconds, calendar.get(Calendar.SECOND).toFloat())
        contur = typedArray.getFloat(R.styleable.CustomClock_contur, 0f)
    }

    private fun drawPointer(canvas: Canvas) {
        // hour pointer
        canvas.rotate(180f)
        canvas.save()
        timePointer.setColor(pointer_hour_color)
        timePointer.setStrokeWidth(pointer_hour_width)
        canvas.rotate(Hour, 0f, 0f)
        canvas.drawLine(
            0f, -20f, 0f,
            (radius * 0.45f) , timePointer
        )
        canvas.restore()

        // minute pointer
        canvas.save()
        timePointer.setColor(pointer_minute_color)
        timePointer.setStrokeWidth(pointer_minute_width)
        canvas.rotate(Minute , 0f, 0f)
        canvas.drawLine(
            0f, -20f, 0f,
            (radius * 0.6f) , timePointer
        )
        canvas.restore()

        // seconds point
        canvas.save()
        timePointer.setColor(pointer_seconds_color)
        timePointer.setStrokeWidth(pointer_seconds_width)
        canvas.rotate(Seconds, 0f, 0f)
        canvas.drawLine(
            0f, -40f, 0f,
            (radius * 0.75f) , timePointer
        )
        canvas.restore()
        //draw point in center of pointers
        timePointer.setColor(pointer_seconds_color)
        canvas.drawCircle(0f, 0f, pointer_hour_width/2, timePointer)
    }
    private fun numerical(canvas: Canvas){
        if(numerical){
            numPaint.setColor(number_color)
            for (i in 0..11){

                canvas.save()
                val textBound = Rect()
                numPaint.textSize = radius/5f
                canvas.translate(
                    0f,
                    -radius + contur + special_mark_length + radius/10f
                )
                var text: String
                if(i == 0){
                    text = "12"
                }
                else {
                    text = i.toString()
                }
                numPaint.getTextBounds(text, 0, text.length, textBound)
                canvas.rotate((-(i) * 30).toFloat()) // Поскольку холст был повернут, вам нужно нарисовать холст прямо и нарисовать числа

                canvas.drawText(
                    text, (-textBound.width() / 2).toFloat(),
                    (textBound.height() / 2).toFloat(), numPaint
                )
                canvas.restore()
                canvas.rotate(30f)
            }
        }
    }
    private fun getRoundedBitmap(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, width, height)
        val radius = (Math.min(width, height) / 2).toFloat() - contur
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

    private fun dial(canvas: Canvas){
        mCirclePaint.setColor(conturColor)
        // the outline of the dial
        canvas.drawCircle(0f, 0f, radius, mCirclePaint)

        //get bitmap from drawable
        val bitmap: Bitmap = dial!!.toBitmap(radius.toInt() *2, radius.toInt() * 2)

        //creatre matrix to draw dial
        val matrix = Matrix()

        //set matrix radius and position
        matrix.mapRadius(radius / 2f)
        matrix.setTranslate(-radius, -radius)

        //round bitmap
        val roundBitmap: Bitmap = getRoundedBitmap(bitmap)

        //draw bitmap
        canvas.drawBitmap(roundBitmap, matrix, mCirclePaint)

        markPaint.strokeWidth = mark_width
        // paint dial
        for (i in 0..59) {
            if (i % 5 == 0) { //special mark for 5 minutes
                markPaint.color = special_mark_color
                canvas.drawLine(
                    0f,
                    radius - contur,
                    0f,
                    radius - contur - special_mark_length,
                    markPaint
                )
            }
            else {
                //mark for minutes
                markPaint.color = mark_color
                canvas.drawLine(
                    0f,
                    radius - contur,
                    0f,
                    radius - contur - mark_length,
                    markPaint
                )
            }
            // Быстро установить масштаб, вращая холст
            canvas.rotate(6f)
        }

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 100 // Предполагаемая ширина View
        val desiredHeight = 100 // Предполагаемая высота View

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize // Задан конкретный размер для ширины
            MeasureSpec.AT_MOST -> min(desiredWidth, widthSize) // Размер не должен превышать заданный размер
            else -> desiredWidth // Задать предпочтительный размер, если точного или максимального размера не задано
        }

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize // Задан конкретный размер для высоты
            MeasureSpec.AT_MOST -> min(desiredHeight, heightSize) // Размер не должен превышать заданный размер
            else -> desiredHeight // Задать предпочтительный размер, если точного или максимального размера не задано
        }

        if(height > width){
            radius = width / 2f
        }
        else {
            radius = height / 2f
        }
        mark_length = radius / 10f
        mark_width = radius / 54f
        special_mark_length = radius / 6f
        pointer_hour_width = radius/30f
        pointer_minute_width = pointer_hour_width/1.8f
        pointer_seconds_width = pointer_hour_width/2.4f
        setMeasuredDimension(width, height) // Устанавливаем фактический размер View
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if(h > w){
            radius = w / 2f
        }
        else {
            radius = h / 2f
        }
        mark_length = radius / 10f
        special_mark_length = radius / 6f
        pointer_hour_width = radius/30f
        pointer_minute_width = pointer_hour_width/1.8f
        pointer_seconds_width = pointer_hour_width/2.4f
        mark_width = radius / 54f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(width/2f,height/2f);
        dial(canvas)
        numerical(canvas)
        drawPointer(canvas)
    }




    fun setContur(contur: Float){
        this.contur = contur
        invalidate()
    }
    fun setConturColor(color:Int){
        conturColor = color
        invalidate()
    }
    fun setMarkColor(color:Int){
        mark_color = color
        invalidate()
    }
    fun setSpecialMarkColor(color: Int){
        special_mark_color = color
        invalidate()
    }
    fun setPointerHourColor(color: Int){
        pointer_hour_color = color
        invalidate()
    }
    fun setPointerMinuteColor(color: Int){
        pointer_minute_color = color
        invalidate()
    }
    fun setPointerSecondsColor(color: Int){
        pointer_seconds_color = color
        invalidate()
    }
    fun numberVisible(flag: Boolean){
        numerical = flag
        invalidate()
    }
    fun setDialBackground(color: Int){
        dial = ColorDrawable(color)
        invalidate()
    }
    fun setDialBackground_Image(drawable_id:Int){
        dial = resources.getDrawable(drawable_id, context.theme)
        invalidate()
    }
    fun setDialBackground_Image(drawable: Drawable){
        dial = drawable
        invalidate()
    }
    fun setHour(hour: Float){
        this.Hour = hour * 15
        invalidate()
    }
    fun setMinute(minute: Float){
        this.Minute = minute * 6
        invalidate()
    }
    fun setSeconds(seconds: Float){
        this.Seconds = seconds * 6
        invalidate()
    }
}