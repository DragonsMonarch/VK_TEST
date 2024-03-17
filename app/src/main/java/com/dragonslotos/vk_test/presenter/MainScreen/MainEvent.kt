package com.dragonslotos.vk_test.presenter.MainScreen

import android.graphics.drawable.Drawable

//Differents Main Events
interface MainEvent
class GetTimeEvent():MainEvent
class SaveImageDial(val drawable: Drawable):MainEvent