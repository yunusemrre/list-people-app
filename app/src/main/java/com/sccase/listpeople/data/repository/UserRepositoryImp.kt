package com.sccase.listpeople.data.repository

import com.sccase.listpeople.data.local.dataprovider.DataSource
import com.sccase.listpeople.data.model.FetchResponse
import com.sccase.listpeople.data.model.ResultWrapper
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
class UserRepositoryImp @Inject constructor(
    private var dataSource: DataSource
) : UserRepository {

    override suspend fun listUser(
        next: String?,
        callback: suspend (ResultWrapper<FetchResponse>) -> Unit
    ) {
        dataSource.fetch(next) { fetchResponse, fetchError ->
            GlobalScope.launch(Dispatchers.IO) {
                if (fetchResponse != null) {
                    callback.invoke(ResultWrapper.Success(fetchResponse))
                } else if (fetchError != null) {
                    callback.invoke(ResultWrapper.Fail(fetchError.errorDescription))
                }
            }

        }
    }
}