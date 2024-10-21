package com.example.a_test_app.ui.main

import android.util.Log
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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

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

    @OptIn(ExperimentalStdlibApi::class)
    private fun loadProfileData() {
        Log.d("ProfileDataLoader", "--------------------------------")
        Log.d("ProfileDataLoader", "loadProfileData started")

        viewModelScope.launch {
            // Логируем текущий поток и диспетчер
            val currentThread = Thread.currentThread().name
            val currentDispatcher = coroutineContext[CoroutineDispatcher]?.toString() ?: "Unknown Dispatcher"

            Log.d("ProfileDataLoader", "Current thread: $currentThread")
            Log.d("ProfileDataLoader", "Current dispatcher: $currentDispatcher")

            val startTime = System.currentTimeMillis()
            Log.d("ProfileDataLoader", "Loading started")

            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val duration = measureTimeMillis {
                    // Переключаемся на диспетчер IO для операций ввода-вывода
                    val uiElements = withContext(Dispatchers.Unconfined) {
                        val currentThread = Thread.currentThread().name
                        val currentDispatcher = coroutineContext[CoroutineDispatcher]?.toString() ?: "Unknown Dispatcher"

                        Log.d("ProfileDataLoader", "Dispatchers.Unconfined Current thread: $currentThread")
                        Log.d("ProfileDataLoader", "Dispatchers.Unconfined Current dispatcher: $currentDispatcher")
                        getProfile.invoke() ?: listOf()
                    }
                    _uiState.value = _uiState.value.copy(uiElements = uiElements, isLoading = false)
                }

                Log.d("ProfileDataLoader", "Loading completed in $duration ms")
            } catch (e: Exception) {
                Log.e("ProfileDataLoader", "Error occurred: ${e.message}", e)
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            } finally {
                Log.d("ProfileDataLoader", "loadProfileData finished")
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
