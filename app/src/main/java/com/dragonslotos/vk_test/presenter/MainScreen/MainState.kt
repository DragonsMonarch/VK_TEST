package com.dragonslotos.vk_test.presenter.MainScreen

import android.graphics.drawable.Drawable

//State Mvi
data class MainState(
    val hour:Float,
    val minute:Float,
    val second: Float,
    val day: String,
    val month: String,
    val day_number: Int,
    val dial:Drawable?
)