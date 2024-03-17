package com.dragonslotos.vk_test.presenter.MainScreen

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dragonslotos.vk_test.domain.usecases.GetCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.concurrent.thread
import kotlin.math.min


@HiltViewModel
class MainViewModel @Inject constructor(getCurrentTime: GetCurrentTime): ViewModel() {

    //Inject UseCase
    private val getCurrentTime: GetCurrentTime = getCurrentTime
    private val state = MutableLiveData<MainState>()

    val result: LiveData<MainState> =state


    //Time holder
    private val subscription: Disposable = Observable.interval(1, TimeUnit.SECONDS)
        .subscribe { seconds ->

            var hour = state.value!!.hour
            var minute = state.value!!.minute
            var second = state.value!!.second
            second++;
            //Check Time limits
            if(second > 60f){
                second = 0f
                minute++
            }
            if(minute > 60f){
                minute = 0f
                hour++
            }
            if(hour >= 24f){
                hour = 0f
            }
            //push time
            state.postValue(
            MainState(
                hour = hour,
                minute = minute,
                second = second,
                day =  state.value!!.day,
                day_number = state.value!!.day_number,
                month = state.value!!.month,
                dial = state.value!!.dial)) }



    //get time
    private fun getTime(){
        //create thread
        thread {
            //get time and parsing for push
           val time =  getCurrentTime.getTime()
            if(time != null){
                state.postValue(MainState(
                    hour = time.hour.toFloat(),
                    minute = time.minute.toFloat(),
                    second = time.second.toFloat(),
                    day =  time.dayOfWeek!!.name,
                    day_number = time.dayOfMonth,
                    month = time.month.name,
                    dial = state.value?.dial
                ))
            }
        }
    }
    // save dialImage in viewmodel for example lifecycle
    private fun saveDial(drawable: Drawable){
        state.postValue(
            MainState(
                hour = state.value!!.hour,
                minute = state.value!!.minute,
                second = state.value!!.second,
                day =  state.value!!.day,
                day_number = state.value!!.day_number,
                month = state.value!!.month,
                dial = drawable))
    }

    //get Event
    fun send(event: MainEvent){
        when(event){
            is GetTimeEvent ->{
                getTime()
            }
            is SaveImageDial ->{
                saveDial(event.drawable)
            }
        }
    }
}