package com.example.a_test_app.ui_logic.main

import com.example.common.ui.UIElement
import com.example.data.model.RootJSON

data class MainUIState(
    val uiElements: List<UIElement> = emptyList(),
    val isEditing: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)