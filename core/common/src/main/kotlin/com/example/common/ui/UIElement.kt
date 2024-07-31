package com.example.common.ui

public data class UIElement(
    val id: String,
    val type: ElementType,
    val label: String? = null,
    val hint: String? = null,
    val options: List<String>? = null, // Для Spinner
    val isVisible: Boolean = true,
    val isEnabled: Boolean = true,
    var field: String? = null
)