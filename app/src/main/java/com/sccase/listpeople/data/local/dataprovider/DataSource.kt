package com.sccase.listpeople.data.local.dataprovider

interface DataSource {
    fun fetch(next: String?, completionHandler: FetchCompletionHandler)
}