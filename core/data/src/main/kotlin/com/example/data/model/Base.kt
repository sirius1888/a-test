package com.example.data.model

import com.example.common.ui.UIElement

public inline fun <reified T> createProfileElement(element: UIElement): ProfileElement {
    val profileElement = when (T::class) {
        Button::class -> Button(
            Id = element.id,
            Label = element.label ?: "",
            Hide = !element.isVisible,
            Disabled = !element.isEnabled,
            Action = null
        ) as ProfileElement
        LastName::class -> LastName(
            Id = element.id,
            Label = element.hint ?: "",
            Hide = !element.isVisible,
            Disabled = !element.isEnabled,
            IgnoreCustomerData = false,
            Validators = emptyList(),
            Field = element.field
        ) as ProfileElement
        Gender::class -> Gender(
            Id = element.id,
            Label = element.label ?: "",
            Hide = !element.isVisible,
            Disabled = !element.isEnabled,
            IgnoreCustomerData = false,
            SupportValues = element.options ?: emptyList(),
            GenderValueMaps = emptyList(),
            Field = element.field
        ) as ProfileElement
        Birthday::class -> Birthday(
            Id = element.id,
            Label = element.hint ?: "",
            Hide = !element.isVisible,
            Disabled = !element.isEnabled,
            IgnoreCustomerData = false,
            Validators = emptyList(),
            Field = element.field
        ) as ProfileElement
        else -> throw IllegalArgumentException("Unsupported type")
    }
    return ProfileElement::class.constructors.first().call(profileElement)
}