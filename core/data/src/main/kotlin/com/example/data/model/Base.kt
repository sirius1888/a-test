package com.example.data.model

import com.example.common.ui.UIElement

public inline fun <reified T> createProfileElement(element: UIElement): ProfileElement {
    return when (T::class) {
        Button::class -> ProfileElement(
            Button = Button(
                Id = element.id,
                Label = element.label ?: "",
                Hide = !element.isVisible,
                Disabled = !element.isEnabled,
                Action = null
            )
        )
        LastName::class -> ProfileElement(
            LastName = LastName(
                Id = element.id,
                Label = element.hint ?: "",
                Hide = !element.isVisible,
                Disabled = !element.isEnabled,
                IgnoreCustomerData = false,
                Validators = emptyList(),
                Field = element.field
            )
        )
        Gender::class -> ProfileElement(
            Gender = Gender(
                Id = element.id,
                Label = element.label ?: "",
                Hide = !element.isVisible,
                Disabled = !element.isEnabled,
                IgnoreCustomerData = false,
                SupportValues = element.options ?: emptyList(),
                GenderValueMaps = emptyList(),
                Field = element.field
            )
        )
        Birthday::class -> ProfileElement(
            Birthday = Birthday(
                Id = element.id,
                Label = element.hint ?: "",
                Hide = !element.isVisible,
                Disabled = !element.isEnabled,
                IgnoreCustomerData = false,
                Validators = emptyList(),
                Field = element.field
            )
        )
        else -> throw IllegalArgumentException("Unsupported type")
    }
}
