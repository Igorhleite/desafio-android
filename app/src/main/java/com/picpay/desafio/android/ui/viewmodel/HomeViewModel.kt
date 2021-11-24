package com.picpay.desafio.android.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.data.RequestState
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.domain.remote.GetUsers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val getUsers: GetUsers
) : ViewModel() {

    private val _userEvent = MutableLiveData<RequestState<List<User>>>()
    val userEvent : LiveData<RequestState<List<User>>> get() = _userEvent

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            _userEvent.value = getUsers.invoke().onStart {
                _userEvent.value = RequestState.Loading(true)
            }.onCompletion {
                _userEvent.value = RequestState.Loading(false)
            }.first()
        }
    }
}