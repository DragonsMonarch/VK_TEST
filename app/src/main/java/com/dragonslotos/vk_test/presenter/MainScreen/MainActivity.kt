package com.dragonslotos.vk_test.presenter.MainScreen


import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dragonslotos.vk_test.R
import com.dragonslotos.vk_test.presenter.CustomClock
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.min


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var clock: CustomClock
    lateinit var monthText: TextView
    lateinit var dayText: TextView
    lateinit var clockSize: SeekBar
    lateinit var mainLayout:LinearLayout
    lateinit var button: Button

    val viewModel: MainViewModel by viewModels<MainViewModel>()
    var currentState: MainState? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val inputStream = contentResolver.openInputStream(it)
            val drawable = Drawable.createFromStream(inputStream, it.toString())
            if (drawable != null){
                viewModel.send(SaveImageDial(drawable))
            }
        }
    }
    //view logic
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //get views from layout
        clock = findViewById(R.id.clock)
        monthText = findViewById(R.id.month)
        dayText = findViewById(R.id.day)
        clockSize = findViewById(R.id.seekBar)
        mainLayout = findViewById(R.id.mainLayout)
        button = findViewById(R.id.photo)


        // get display parameters
        var width = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowManager.currentWindowMetrics.bounds.width()
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
        var height = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowManager.currentWindowMetrics.bounds.height()
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }

        //set max and min display for seek bar
        clockSize.max = min(height, width)
        clockSize.min = 100

        //set of size clock on seek bar
        clock.viewTreeObserver.addOnGlobalLayoutListener( object : OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                clockSize.progress = min(clock.measuredHeight, clock.measuredWidth)
                //delet listener when goal is done
                clock.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        });

        //seekBar change layout params of clock
        clockSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var params: LinearLayout.LayoutParams = clock.layoutParams as LinearLayout.LayoutParams
                params.height = progress
                params.width = progress
                clock.layoutParams = params
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        //button to get image dial
        button.setOnClickListener {
            getContent.launch("image/*")
        }
        //Send get event
        viewModel.send(GetTimeEvent())
        //Parsing state
        viewModel.result.observe(this){ state ->
            if(state != null){
                if(!(state.day.equals(currentState?.day))){
                    dayText.text = state.day
                }
                if(!(state.day_number == currentState?.day_number ||state.month == currentState?.month)) {
                    monthText.text = state.day_number.toString() + " " + state.month
                }
                if(!(state.hour == currentState?.hour)) {
                    clock.setHour(state.hour)
                }
                if(!(state.minute == currentState?.minute)) {
                    clock.setMinute(state.minute)
                }
                if(!(state.second == currentState?.second)) {
                    clock.setSeconds(state.second)
                }
                if(!(state.dial == currentState?.dial)){
                    clock.setDialBackground_Image(state.dial!!)
                }
                currentState = state
            }
        }
    }

    override fun onStart() {
        super.onStart()
        clockSize.progress = min(clock.measuredHeight, clock.measuredWidth)
    }

}
