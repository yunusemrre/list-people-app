package com.sccase.listpeople.data.model

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {
    enum class Status {
        SUCCESS,
        ERROR,
        //FAILURE
    }

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }
    }
}