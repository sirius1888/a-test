package com.example.a_test_app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a_test_app.ui_logic.main.GetProfileUseCase
import com.example.a_test_app.ui_logic.main.MainUIState
import com.example.a_test_app.ui_logic.main.UpdateProfileUseCase
import com.example.common.ui.ElementType
import com.example.data.model.RootJSON
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProfile: GetProfileUseCase,
    private val updateProfile: UpdateProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUIState())
    val uiState: StateFlow<MainUIState> get() = _uiState

    init {
        loadProfileData()
    }

    fun loadProfileData() {
        viewModelScope.launch {
            val profile = getProfile.invoke()
            updateProfileElements(profile)
        }
    }

    private fun updateProfileElements(profile: RootJSON?) {
        profile?.let {
            _uiState.value = _uiState.value.copy(
                profile = profile,
                uiElements = convertProfileToUIElements(profile.ProfileElements)
            )
        }
    }

    fun setEditing(state: Boolean) {
        _uiState.value = _uiState.value.copy(
            isEditing = state,
            uiElements = _uiState.value.uiElements.map { element ->
                if (
                    element.type == ElementType.EDIT_TEXT ||
                    element.type == ElementType.EDIT_TEXT_CALENDAR ||
                    element.type == ElementType.SPINNER
                ) {
                    element.copy(isEnabled = state)
                } else {
                    element
                }
            }.map {
                when (it.id) {
                    "button_edit_id" -> it.copy(isVisible = !state)
                    "button_save_id" -> it.copy(isVisible = state)
                    "button_cancel_id" -> it.copy(isVisible = state)
                    else -> it
                }
            }
        )
    }

    fun saveProfile() {
        viewModelScope.launch {
            val updatedProfile = RootJSON(
                TestMode = _uiState.value.profile?.TestMode ?: false,
                EmulationMode = _uiState.value.profile?.EmulationMode ?: false,
                ProfileElements = convertUIElementsToProfile(_uiState.value.uiElements)
            )
            updateProfile.update(updatedProfile)
            setEditing(false)
        }
    }
}
