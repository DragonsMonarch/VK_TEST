package com.dragonslotos.vk_test.data.repository

import android.util.Log
import com.dragonslotos.vk_test.data.retrofit.DataHolder
import com.dragonslotos.vk_test.domain.model.DateTime
import com.dragonslotos.vk_test.domain.repository.DateTimeRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

//Get time response
class GetDateTime  @Inject constructor(dataHolder: DataHolder): DateTimeRepository {
    private val dataHolder = dataHolder
    override fun getRequest(): DateTime?{
        try{
            val response= dataHolder.time.execute()
            Log.d("checker2", response.body().toString())
            return response.body()
        } catch (e: SocketTimeoutException){
            Log.d("fail", "fail")
            return null
        }
        catch (e: UnknownHostException){
            return null
        }
    }
}