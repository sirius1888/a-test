package com.example.common.ui

public data class UIElement(
    val id: String,
    val type: ElementType,
    val label: String? = null,
    val hint: String? = null,
    val options: List<String>? = null, // Только Для Spinner
    val isVisible: Boolean = true,
    val isEnabled: Boolean = true,
    var field: String? = null,
    var children: List<UIElement>? = null
)
public fun UIElement.setVisibility(isVisible: Boolean): UIElement {
    return this.copy(isVisible = isVisible)
}

public fun UIElement.setEnabled(isEnabled: Boolean): UIElement {
    return this.copy(isEnabled = isEnabled)
}