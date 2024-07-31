package com.example.a_test_app.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a_test_app.ui_logic.main.GetProfileUseCase
import com.example.a_test_app.ui_logic.main.UpdateProfileUseCase
import com.example.common.ui.ElementType
import com.example.common.ui.UIElement
import com.example.data.model.RootJSON
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
    private val getProfile: GetProfileUseCase,
    private val updateProfile: UpdateProfileUseCase
) : ViewModel() {

    private val _profile = MutableStateFlow<RootJSON?>(null)
    val profile: StateFlow<RootJSON?> get() = _profile

    private val _uiElements = MutableStateFlow<List<UIElement>>(emptyList())
    val uiElements: StateFlow<List<UIElement>> get() = _uiElements

    private val _isEditing = MutableStateFlow(false)
    val isEditing: StateFlow<Boolean> get() = _isEditing

    init {
        loadProfileData()
    }

    fun loadProfileData() {
        viewModelScope.launch {
            val profile = getProfile.invoke()
            updateProfileElements(profile)
        }
    }

    fun updateProfileElements(profile: RootJSON?) {
        profile?.let {
            _profile.value = profile
            _uiElements.value = convertProfileToUIElements(profile.ProfileElements)
        }
    }

    fun isEditing(state: Boolean) {
        _isEditing.value = state
        _uiElements.value = _uiElements.value.map { element ->
            if (
                element.type == ElementType.EDIT_TEXT ||
                element.type == ElementType.EDIT_TEXT_CALENDAR ||
                element.type == ElementType.SPINNER
            ) {
                element.copy(isEnabled = state)
            } else {
                element
            }
        }
        _uiElements.value = _uiElements.value.map {
            when (it.id) {
                "button_edit_id" -> it.copy(isVisible = !state)
                "button_save_id" -> it.copy(isVisible = state)
                "button_cancel_id" -> it.copy(isVisible = state)
                else -> it
            }
        }
    }

    fun saveProfile() {
        viewModelScope.launch {
            val updatedProfile = RootJSON(
                TestMode = _profile.value?.TestMode ?: false,
                EmulationMode = _profile.value?.EmulationMode ?: false,
                ProfileElements = convertUIElementsToProfile(_uiElements.value)
            )
            updateProfile.update(updatedProfile)
            isEditing(false)
        }
    }
}