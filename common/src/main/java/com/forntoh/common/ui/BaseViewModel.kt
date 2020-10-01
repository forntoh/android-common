package com.forntoh.common.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.forntoh.common.internal.data.InputError

@Suppress("PropertyName", "MemberVisibilityCanBePrivate")
abstract class BaseViewModel : ViewModel() {

    protected val _inputError = MutableLiveData<InputError>()
    val inputError = _inputError

}