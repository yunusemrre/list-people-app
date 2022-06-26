package com.sccase.listpeople.data.model

data class ProcessResult(
    val fetchResponse: FetchResponse?,
    val fetchError: FetchError?,
    val waitTime: Double
)