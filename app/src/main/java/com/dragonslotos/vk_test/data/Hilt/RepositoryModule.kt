package com.dragonslotos.vk_test.data.Hilt

import com.dragonslotos.vk_test.data.repository.GetDateTime
import com.dragonslotos.vk_test.data.retrofit.DataHolder
import com.dragonslotos.vk_test.domain.repository.DateTimeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


//Impements repository dependecy
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun getDateTimeRepository(dataHolder: DataHolder): DateTimeRepository {
        return GetDateTime(dataHolder)
    }
}