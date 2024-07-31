package com.example.a_test_app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a_test_app.ui_logic.main.GetProfileUseCase
import com.example.a_test_app.ui_logic.main.MainUIState
import com.example.a_test_app.ui_logic.main.UpdateProfileUseCase
import com.example.common.ui.ElementType
import com.example.common.ui.UIElement
import com.example.common.ui.setEnabled
import com.example.common.ui.setVisibility
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

    private val _uiState = MutableStateFlow(MainUIState(isLoading = true))
    val uiState: StateFlow<MainUIState> get() = _uiState

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val uiElements = getProfile.invoke() ?: listOf()
                _uiState.value = _uiState.value.copy(uiElements = uiElements, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun updateProfile() {
        viewModelScope.launch {
            updateProfile.update(_uiState.value.uiElements)
            onSaveButtonClick()
        }
    }

    fun onEditButtonClick() {
        updateUIStateForEditing(true)
    }

    fun onSaveButtonClick() {
        updateUIStateForEditing(false)
    }

    fun onCancelButtonClick() {
        updateUIStateForEditing(false)
    }

    private fun updateUIStateForEditing(isEditing: Boolean) {
        _uiState.value = _uiState.value.copy(
            isEditing = isEditing,
            uiElements = updateElementsRecursively(_uiState.value.uiElements) { element ->
                when (element.id) {
                    "button_edit_id" -> element.setVisibility(!isEditing)
                    "button_save_id", "button_cancel_id" -> element.setVisibility(isEditing)
                    else -> element.setEnabled(isEditing)
                }
            }
        )
    }

    private fun updateElementsRecursively(
        elements: List<UIElement>,
        update: (UIElement) -> UIElement
    ): List<UIElement> {
        return elements.map { element ->
            if (element.type == ElementType.COLUMN || element.type == ElementType.ROW) {
                element.copy(
                    children = updateElementsRecursively(
                        element.children ?: emptyList(),
                        update
                    )
                )
            } else {
                update(element)
            }
        }
    }
}
