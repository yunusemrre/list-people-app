package com.sccase.listpeople.data.repository

import com.sccase.listpeople.data.model.FetchResponse
import com.sccase.listpeople.data.model.ResultWrapper

interface UserRepository {
    suspend fun listUser(next: String?, callback: suspend (ResultWrapper<FetchResponse>) -> Unit)
}