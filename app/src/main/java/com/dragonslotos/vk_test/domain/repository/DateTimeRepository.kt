package com.dragonslotos.vk_test.domain.repository

import com.dragonslotos.vk_test.domain.model.DateTime

//Interface for datime repository
interface DateTimeRepository {
    public fun getRequest(): DateTime?
}