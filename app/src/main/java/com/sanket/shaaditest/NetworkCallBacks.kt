package com.sanket.shaaditest

interface NetworkCallBacks {
    fun onSuccess(response: String)
    fun onFailure(errorMessage: String)
}