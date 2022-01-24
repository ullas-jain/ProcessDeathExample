package com.ullas.processdeathexample

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var nameField by mutableStateOf(savedStateHandle.get("nameKey") ?: "")
        private set

    var emailField by mutableStateOf(savedStateHandle.get("emailKey") ?: "")
        private set

    fun onNameChange(updatedName: CharSequence) {
        nameField = updatedName.toString()
        savedStateHandle.set("nameKey", nameField)
    }

    fun onEmailChange(updatedName: CharSequence) {
        emailField = updatedName.toString()
        savedStateHandle.set("emailKey", emailField)
    }
}