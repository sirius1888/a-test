package com.example.a_test_app.ui.main

import com.example.common.ui.ElementType
import com.example.common.ui.UIElement
import com.example.data.model.Birthday
import com.example.data.model.Button
import com.example.data.model.Column
import com.example.data.model.Gender
import com.example.data.model.LastName
import com.example.data.model.ProfileElement
import com.example.data.model.Row
import javax.inject.Inject


class ProfileMapper @Inject constructor() {
    fun convertUIElementsToProfile(uiElements: List<UIElement>): List<ProfileElement> {
        val elements = mutableListOf<ProfileElement>()
        var column: Column? = null
        var row: Row? = null
        var button: Button? = null
        var lastName: LastName? = null
        var gender: Gender? = null
        var birthday: Birthday? = null

        for (element in uiElements) {
            when (element.type) {
                ElementType.BUTTON -> {
                    button = Button(
                        Id = element.id,
                        Label = element.label ?: "",
                        Hide = !element.isVisible,
                        Disabled = !element.isEnabled,
                        Action = null
                    )
                    row?.let {
                        elements.add(ProfileElement(Row = it.copy(ProfileElements = elements)))
                        row = null
                    }
                    column?.let {
                        elements.add(ProfileElement(Column = it.copy(ProfileElements = elements)))
                        column = null
                    }
                    elements.add(ProfileElement(Button = button))
                    button = null
                }

                ElementType.EDIT_TEXT -> {
                    lastName = LastName(
                        Id = element.id,
                        Label = element.hint ?: "",
                        Hide = !element.isVisible,
                        Disabled = !element.isEnabled,
                        IgnoreCustomerData = false,
                        Validators = emptyList(),
                        Field = element.field
                    )
                    row?.let {
                        elements.add(ProfileElement(Row = it.copy(ProfileElements = elements)))
                        row = null
                    }
                    column?.let {
                        elements.add(ProfileElement(Column = it.copy(ProfileElements = elements)))
                        column = null
                    }
                    elements.add(ProfileElement(LastName = lastName))
                    lastName = null
                }

                ElementType.SPINNER -> {
                    gender = Gender(
                        Id = element.id,
                        Label = element.label ?: "",
                        Hide = !element.isVisible,
                        Disabled = !element.isEnabled,
                        IgnoreCustomerData = false,
                        SupportValues = element.options ?: emptyList(),
                        GenderValueMaps = emptyList(),
                        Field = element.field
                    )
                    row?.let {
                        elements.add(ProfileElement(Row = it.copy(ProfileElements = elements)))
                        row = null
                    }
                    column?.let {
                        elements.add(ProfileElement(Column = it.copy(ProfileElements = elements)))
                        column = null
                    }
                    elements.add(ProfileElement(Gender = gender))
                    gender = null
                }

                ElementType.EDIT_TEXT_CALENDAR -> {
                    birthday = Birthday(
                        Id = element.id,
                        Label = element.hint ?: "",
                        Hide = !element.isVisible,
                        Disabled = !element.isEnabled,
                        IgnoreCustomerData = false,
                        Validators = emptyList(),
                        Field = element.field
                    )
                    row?.let {
                        elements.add(ProfileElement(Row = it.copy(ProfileElements = elements)))
                        row = null
                    }
                    column?.let {
                        elements.add(ProfileElement(Column = it.copy(ProfileElements = elements)))
                        column = null
                    }
                    elements.add(ProfileElement(Birthday = birthday))
                    birthday = null
                }
            }
        }

        row?.let {
            elements.add(ProfileElement(Row = it.copy(ProfileElements = elements)))
        }
        column?.let {
            elements.add(ProfileElement(Column = it.copy(ProfileElements = elements)))
        }

        return elements
    }

    fun convertProfileToUIElements(profileElements: List<ProfileElement>): List<UIElement> {
        val elements = mutableListOf<UIElement>()

        for (element in profileElements) {
            element.Column?.let { column ->
                elements.addAll(convertProfileToUIElements(column.ProfileElements))
            }
            element.Row?.let { row ->
                elements.addAll(convertProfileToUIElements(row.ProfileElements))
            }
            element.Button?.let { button ->
                elements.add(
                    UIElement(
                        id = button.Id,
                        type = ElementType.BUTTON,
                        label = button.Label,
                        isVisible = !button.Hide,
                        isEnabled = !button.Disabled,
                        field = null
                    )
                )
            }
            element.LastName?.let { lastName ->
                elements.add(
                    UIElement(
                        id = lastName.Id,
                        type = ElementType.EDIT_TEXT,
                        hint = lastName.Label,
                        isVisible = !lastName.Hide,
                        isEnabled = !lastName.Disabled,
                        field = lastName.Field
                    )
                )
            }
            element.Gender?.let { gender ->
                elements.add(
                    UIElement(
                        id = gender.Id,
                        type = ElementType.SPINNER,
                        label = gender.Label,
                        options = gender.SupportValues,
                        isVisible = !gender.Hide,
                        field = gender.Field
                    )
                )
            }
            element.Birthday?.let { birthday ->
                elements.add(
                    UIElement(
                        id = birthday.Id,
                        type = ElementType.EDIT_TEXT_CALENDAR,
                        hint = birthday.Label,
                        isVisible = !birthday.Hide,
                        isEnabled = !birthday.Disabled,
                        field = birthday.Field
                    )
                )
            }
        }

        return elements
    }

}

//fun convertUIElementsToProfile(elements: List<UIElement>): List<ProfileElement> {
//    val profileElements = mutableListOf<ProfileElement>()
//
//    fun buildProfileElements(uiElements: List<UIElement>): List<ProfileElement> {
//        val elements = mutableListOf<ProfileElement>()
//        var column: Column? = null
//        var row: Row? = null
//        var button: Button? = null
//        var lastName: LastName? = null
//        var gender: Gender? = null
//        var birthday: Birthday? = null
//        var field: String? = null
//
//        for (element in uiElements) {
//            when (element.type) {
//                ElementType.BUTTON -> {
//                    button = Button(
//                        Id = element.id,
//                        Label = element.label ?: "",
//                        Hide = !element.isVisible,
//                        Disabled = !element.isEnabled,
//                        Action = null
//                    )
//                    row?.let {
//                        elements.add(ProfileElement(Row = it.copy(ProfileElements = elements)))
//                        row = null
//                    }
//                    column?.let {
//                        elements.add(ProfileElement(Column = it.copy(ProfileElements = elements)))
//                        column = null
//                    }
//                    elements.add(ProfileElement(Button = button))
//                    button = null
//                }
//
//                ElementType.EDIT_TEXT -> {
//                    lastName = LastName(
//                        Id = element.id,
//                        Label = element.hint ?: "",
//                        Hide = !element.isVisible,
//                        Disabled = !element.isEnabled,
//                        IgnoreCustomerData = false,
//                        Validators = emptyList(),
//                        Field = element.field
//                    )
//                    row?.let {
//                        elements.add(ProfileElement(Row = it.copy(ProfileElements = elements)))
//                        row = null
//                    }
//                    column?.let {
//                        elements.add(ProfileElement(Column = it.copy(ProfileElements = elements)))
//                        column = null
//                    }
//                    elements.add(ProfileElement(LastName = lastName))
//                    lastName = null
//                }
//
//                ElementType.SPINNER -> {
//                    gender = Gender(
//                        Id = element.id,
//                        Label = element.label ?: "",
//                        Hide = !element.isVisible,
//                        Disabled = !element.isEnabled,
//                        IgnoreCustomerData = false,
//                        SupportValues = element.options ?: emptyList(),
//                        GenderValueMaps = emptyList(),
//                        Field = element.field
//                    )
//                    row?.let {
//                        elements.add(ProfileElement(Row = it.copy(ProfileElements = elements)))
//                        row = null
//                    }
//                    column?.let {
//                        elements.add(ProfileElement(Column = it.copy(ProfileElements = elements)))
//                        column = null
//                    }
//                    elements.add(ProfileElement(Gender = gender))
//                    gender = null
//                }
//
//                ElementType.EDIT_TEXT_CALENDAR -> {
//                    birthday = Birthday(
//                        Id = element.id,
//                        Label = element.hint ?: "",
//                        Hide = !element.isVisible,
//                        Disabled = !element.isEnabled,
//                        IgnoreCustomerData = false,
//                        Validators = emptyList(),
//                        Field = element.field
//                    )
//                    row?.let {
//                        elements.add(ProfileElement(Row = it.copy(ProfileElements = elements)))
//                        row = null
//                    }
//                    column?.let {
//                        elements.add(ProfileElement(Column = it.copy(ProfileElements = elements)))
//                        column = null
//                    }
//                    elements.add(ProfileElement(Birthday = birthday))
//                    birthday = null
//                }
//            }
//        }
//
//        row?.let {
//            elements.add(ProfileElement(Row = it.copy(ProfileElements = elements)))
//        }
//        column?.let {
//            elements.add(ProfileElement(Column = it.copy(ProfileElements = elements)))
//        }
//
//        return elements
//    }
//
//    return buildProfileElements(elements)
//}
//
//fun convertProfileToUIElements(elements: List<ProfileElement>): List<UIElement> {
//    val uiElements = mutableListOf<UIElement>()
//
//    fun buildUIElements(profileElements: List<ProfileElement>): List<UIElement> {
//        val elements = mutableListOf<UIElement>()
//
//        for (element in profileElements) {
//            element.Column?.let { column ->
//                elements.addAll(buildUIElements(column.ProfileElements))
//            }
//            element.Row?.let { row ->
//                elements.addAll(buildUIElements(row.ProfileElements))
//            }
//            element.Button?.let { button ->
//                elements.add(
//                    UIElement(
//                        id = button.Id,
//                        type = ElementType.BUTTON,
//                        label = button.Label,
//                        isVisible = !button.Hide,
//                        isEnabled = !button.Disabled,
//                        field = null
//                    )
//                )
//            }
//            element.LastName?.let { lastName ->
//                elements.add(
//                    UIElement(
//                        id = lastName.Id,
//                        type = ElementType.EDIT_TEXT,
//                        hint = lastName.Label,
//                        isVisible = !lastName.Hide,
//                        isEnabled = !lastName.Disabled,
//                        field = lastName.Field
//                    )
//                )
//            }
//            element.Gender?.let { gender ->
//                elements.add(
//                    UIElement(
//                        id = gender.Id,
//                        type = ElementType.SPINNER,
//                        label = gender.Label,
//                        options = gender.SupportValues,
//                        isVisible = !gender.Hide,
//                        field = gender.Field
//                    )
//                )
//            }
//            element.Birthday?.let { birthday ->
//                elements.add(
//                    UIElement(
//                        id = birthday.Id,
//                        type = ElementType.EDIT_TEXT_CALENDAR,
//                        hint = birthday.Label,
//                        isVisible = !birthday.Hide,
//                        isEnabled = !birthday.Disabled,
//                        field = birthday.Field
//                    )
//                )
//            }
//        }
//
//        return elements
//    }
//
//    return buildUIElements(elements)
//}