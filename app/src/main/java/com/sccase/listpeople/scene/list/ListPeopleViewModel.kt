package com.sccase.listpeople.scene.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sccase.listpeople.data.model.FetchResponse
import com.sccase.listpeople.data.model.Resource
import com.sccase.listpeople.data.model.ResultWrapper
import com.sccase.listpeople.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPeopleViewModel @Inject constructor(
    private var repository: UserRepository
) : ViewModel() {

    /**
     * Mutable User list by [FetchResponse]
     */
    private val _userListData = MutableLiveData<Resource<FetchResponse>>()
    val userListData: LiveData<Resource<FetchResponse>> get() = _userListData

    /**
     * Current state of process
     * Prevent multi call of [listUser]
     */
    private val _processState = MutableLiveData(false)
    val processState: LiveData<Boolean> get() = _processState

    /**
     * Next value of pagination
     */
    private var nextValue: String? = null

    /**
     * List user list using [nextValue]
     */
    fun listUser() {
        if (processState.value == false) {
            _processState.postValue(true)
            viewModelScope.launch(Dispatchers.IO) {
                repository.listUser(nextValue) { resultWrapper ->
                    when (resultWrapper) {
                        is ResultWrapper.Success -> {
                            _userListData.postValue(Resource.success(resultWrapper.value))
                            nextValue = resultWrapper.value.next
                            _processState.postValue(false)
                        }
                        is ResultWrapper.Fail -> {
                            _userListData.postValue(Resource.error(resultWrapper.message, null))
                            _processState.postValue(false)
                        }
                    }
                }
            }
        }
    }
}
