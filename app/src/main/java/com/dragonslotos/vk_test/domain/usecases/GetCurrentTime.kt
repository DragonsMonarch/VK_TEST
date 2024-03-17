package com.dragonslotos.vk_test.domain.usecases

import com.dragonslotos.vk_test.data.repository.GetDateTime
import com.dragonslotos.vk_test.domain.model.DateTime
import com.dragonslotos.vk_test.domain.repository.DateTimeRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


//Get time model from repository and parsing it in Local date
class GetCurrentTime @Inject constructor(getDateTime: DateTimeRepository) {
    private val getDateTime = getDateTime

    fun getTime():LocalDateTime?{
        //create formatter for time
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        //get request
        val request = getDateTime.getRequest()

        //check for null request
        if(request != null){
            val dateTime = LocalDateTime.parse(request.datetime.split('.')[0], formatter)
            return  dateTime
        }
        else{
            return null
        }
    }
}