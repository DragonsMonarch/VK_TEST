package com.dragonslotos.vk_test.data.repository

import android.util.Log
import com.dragonslotos.vk_test.data.retrofit.DataHolder
import com.dragonslotos.vk_test.domain.model.DateTime
import com.dragonslotos.vk_test.domain.repository.DateTimeRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

//Get time response
class GetDateTime  @Inject constructor(dataHolder: DataHolder): DateTimeRepository {
    private val dataHolder = dataHolder
    override fun getRequest(): DateTime?{
        val response= dataHolder.time.execute()

        return response.body()
    }
}