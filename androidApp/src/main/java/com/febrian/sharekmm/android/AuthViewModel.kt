package com.febrian.sharekmm.android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febrian.sharekmm.auth.AuthSdk
import com.febrian.sharekmm.response.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val sdk: AuthSdk) : ViewModel() {

    val authState = MutableLiveData<AuthState>()

    fun register(email: String, password: String) {

        authState.value = AuthState.Loading

        viewModelScope.launch {
            authState.value = sdk.register(email, password)
        }
    }

    fun login(email: String, password: String) {

        authState.value = AuthState.Loading

        viewModelScope.launch {
            authState.value = sdk.login(email, password)
        }
    }
}