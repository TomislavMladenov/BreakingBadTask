package com.breaking.bad.business.state


/**
 * Not DataState class that I would use, but I wanted to simplify things
 * My standard implementation is much more complex but handles a wide array of use cases.
 */
sealed class DataState<out R> {

    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}
