package com.example.a_test_app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a_test_app.ui_logic.main.GetProfileUseCase
import com.example.a_test_app.ui_logic.main.MainUIState
import com.example.a_test_app.ui_logic.main.UpdateProfileUseCase
import com.example.common.ui.ElementType
import com.example.common.ui.UIElement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProfile: GetProfileUseCase, private val updateProfile: UpdateProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUIState())
    val uiState: StateFlow<MainUIState> get() = _uiState

    init {
        loadProfileData()
    }

    fun loadProfileData() {
        viewModelScope.launch {
            val uiElements = getProfile.invoke()
            updateProfileElements(uiElements)
        }
    }

    private fun updateProfileElements(uiElements: List<UIElement>?) {
        uiElements?.let {
            _uiState.value = _uiState.value.copy(
                uiElements = it
            )
        }
    }

    fun updateProfile() {
        viewModelScope.launch {
            updateProfile.update(_uiState.value.uiElements)
            onSaveButtonClick()
        }
    }

    fun onEditButtonClick() {
        _uiState.value = _uiState.value.copy(isEditing = true,
            uiElements = updateElementsRecursively(_uiState.value.uiElements) { element ->
                when (element.id) {
                    "button_edit_id" -> element.copy(isVisible = false)
                    "button_save_id", "button_cancel_id" -> element.copy(isVisible = true)
                    else -> element.copy(isEnabled = true) // Включаем редактирование для всех элементов
                }
            })
    }

    fun onSaveButtonClick() {
        _uiState.value = _uiState.value.copy(isEditing = false,
            uiElements = updateElementsRecursively(_uiState.value.uiElements) { element ->
                when (element.id) {
                    "button_edit_id" -> element.copy(isVisible = true)
                    "button_save_id", "button_cancel_id" -> element.copy(isVisible = false)
                    else -> element.copy(isEnabled = false) // Выключаем редактирование для всех элементов
                }
            })
    }

    fun onCancelButtonClick() {
        _uiState.value = _uiState.value.copy(isEditing = false,
            uiElements = updateElementsRecursively(_uiState.value.uiElements) { element ->
                when (element.id) {
                    "button_edit_id" -> element.copy(isVisible = true)
                    "button_save_id", "button_cancel_id" -> element.copy(isVisible = false)
                    else -> element.copy(isEnabled = false) // Выключаем редактирование для всех элементов
                }
            })
    }

    private fun updateElementsRecursively(
        elements: List<UIElement>,
        update: (UIElement) -> UIElement
    ): List<UIElement> {
        return elements.map { element ->
            if (element.type == ElementType.COLUMN ||
                element.type == ElementType.ROW) {
                element.copy(
                    children =
                    updateElementsRecursively(
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