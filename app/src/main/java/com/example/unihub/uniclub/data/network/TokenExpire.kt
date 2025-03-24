package com.example.unihub.uniclub.data.network


import com.example.unihub.uniclub.util.AppPreferencesDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class TokenExpirationHandler(
    private val appPreferencesDataSource: AppPreferencesDataSource
) {
    private val _tokenExpired = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val tokenExpired: SharedFlow<Unit> = _tokenExpired.asSharedFlow()

    suspend fun onTokenExpired() {
        // Clear the stored token
        appPreferencesDataSource.clearAll()

        // Emit an event to notify observers
        _tokenExpired.emit(Unit)
    }
}