package com.example.a_test_app.ui.main

import android.util.Log
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
        var columnElements = mutableListOf<ProfileElement>()
        var rowElements = mutableListOf<ProfileElement>()

        for (element in uiElements) {
            when (element.type) {
                ElementType.BUTTON -> {
                    val button = Button(
                        Id = element.id,
                        Label = element.label ?: "",
                        Hide = !element.isVisible,
                        Disabled = !element.isEnabled,
                        Action = null
                    )
                    rowElements.add(ProfileElement(Button = button))
                }

                ElementType.EDIT_TEXT -> {
                    val lastName = LastName(
                        Id = element.id,
                        Label = element.hint ?: "",
                        Hide = !element.isVisible,
                        Disabled = !element.isEnabled,
                        IgnoreCustomerData = false,
                        Validators = emptyList(),
                        Field = element.field
                    )
                    rowElements.add(ProfileElement(LastName = lastName))
                }

                ElementType.SPINNER -> {
                    val gender = Gender(
                        Id = element.id,
                        Label = element.label ?: "",
                        Hide = !element.isVisible,
                        Disabled = !element.isEnabled,
                        IgnoreCustomerData = false,
                        SupportValues = element.options ?: emptyList(),
                        GenderValueMaps = emptyList(),
                        Field = element.field
                    )
                    rowElements.add(ProfileElement(Gender = gender))
                }

                ElementType.EDIT_TEXT_CALENDAR -> {
                    val birthday = Birthday(
                        Id = element.id,
                        Label = element.hint ?: "",
                        Hide = !element.isVisible,
                        Disabled = !element.isEnabled,
                        IgnoreCustomerData = false,
                        Validators = emptyList(),
                        Field = element.field
                    )
                    rowElements.add(ProfileElement(Birthday = birthday))
                }

                ElementType.COLUMN -> {
                    val nestedElements = convertUIElementsToProfile(element.children ?: emptyList())
                    val column = Column(
                        Id = element.id,
                        Hide = !element.isVisible,
                        ProfileElements = nestedElements
                    )
                    columnElements.add(ProfileElement(Column = column))
                }

                ElementType.ROW -> {
                    val nestedElements = convertUIElementsToProfile(element.children ?: emptyList())
                    val row = Row(
                        Id = element.id,
                        Hide = !element.isVisible,
                        ProfileElements = nestedElements
                    )
                    rowElements.add(ProfileElement(Row = row))
                }
            }
        }

        if (rowElements.isNotEmpty()) {
            elements.add(ProfileElement(Row = Row(Id = "", Hide = false, ProfileElements = rowElements)))
        }
        if (columnElements.isNotEmpty()) {
            elements.add(ProfileElement(Column = Column(Id = "", Hide = false, ProfileElements = columnElements)))
        }

        return elements
    }

    fun convertProfileToUIElements(profileElements: List<ProfileElement>): List<UIElement> {
        val uiElements = mutableListOf<UIElement>()

        profileElements.forEach { profileElement ->
            profileElement.Column?.let { column ->
                val columnElements = convertProfileToUIElements(column.ProfileElements)
                uiElements.add(
                    UIElement(
                        id = column.Id,
                        type = ElementType.COLUMN,
                        children = columnElements,
                        isVisible = !column.Hide
                    )
                )
            }

            profileElement.Row?.let { row ->
                val rowElements = convertProfileToUIElements(row.ProfileElements)
                uiElements.add(
                    UIElement(
                        id = row.Id,
                        type = ElementType.ROW,
                        children = rowElements,
                        isVisible = !row.Hide
                    )
                )
            }

            profileElement.Button?.let { button ->
                uiElements.add(
                    UIElement(
                        id = button.Id,
                        type = ElementType.BUTTON,
                        label = button.Label,
                        isVisible = !button.Hide,
                        isEnabled = !button.Disabled
                    )
                )
            }

            profileElement.LastName?.let { lastName ->
                uiElements.add(
                    UIElement(
                        id = lastName.Id,
                        type = ElementType.EDIT_TEXT,
                        hint = lastName.Label,
                        isVisible = !lastName.Hide,
                        isEnabled = !lastName.Disabled
                    )
                )
            }

            profileElement.Gender?.let { gender ->
                uiElements.add(
                    UIElement(
                        id = gender.Id,
                        type = ElementType.SPINNER,
                        label = gender.Label,
                        isVisible = !gender.Hide,
                        isEnabled = !gender.Disabled,
                        options = gender.SupportValues
                    )
                )
            }

            profileElement.Birthday?.let { birthday ->
                uiElements.add(
                    UIElement(
                        id = birthday.Id,
                        type = ElementType.EDIT_TEXT_CALENDAR,
                        hint = birthday.Label,
                        isVisible = !birthday.Hide,
                        isEnabled = !birthday.Disabled
                    )
                )
            }
        }

        return uiElements
    }

}