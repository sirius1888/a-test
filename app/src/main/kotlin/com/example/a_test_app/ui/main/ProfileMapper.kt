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
            when {
                profileElement.Column != null -> {
                    val columnElements = convertProfileToUIElements(profileElement.Column!!.ProfileElements)
                    uiElements.add(
                        UIElement(
                            id = profileElement.Column!!.Id,
                            type = ElementType.COLUMN,
                            children = columnElements,
                            isVisible = !profileElement.Column!!.Hide
                        )
                    )
                }
                profileElement.Row != null -> {
                    val rowElements = convertProfileToUIElements(profileElement.Row!!.ProfileElements)
                    uiElements.add(
                        UIElement(
                            id = profileElement.Row!!.Id,
                            type = ElementType.ROW,
                            children = rowElements,
                            isVisible = !profileElement.Row!!.Hide
                        )
                    )
                }
                profileElement.Button != null -> {
                    uiElements.add(
                        UIElement(
                            id = profileElement.Button!!.Id,
                            type = ElementType.BUTTON,
                            label = profileElement.Button!!.Label,
                            isVisible = !profileElement.Button!!.Hide,
                            isEnabled = !profileElement.Button!!.Disabled
                        )
                    )
                }
                profileElement.LastName != null -> {
                    uiElements.add(
                        UIElement(
                            id = profileElement.LastName!!.Id,
                            type = ElementType.EDIT_TEXT,
                            hint = profileElement.LastName!!.Label,
                            isVisible = !profileElement.LastName!!.Hide,
                            isEnabled = !profileElement.LastName!!.Disabled
                        )
                    )
                }
                profileElement.Gender != null -> {
                    uiElements.add(
                        UIElement(
                            id = profileElement.Gender!!.Id,
                            type = ElementType.SPINNER,
                            label = profileElement.Gender!!.Label,
                            isVisible = !profileElement.Gender!!.Hide,
                            isEnabled = !profileElement.Gender!!.Disabled,
                            options = profileElement.Gender!!.SupportValues
                        )
                    )
                }
                profileElement.Birthday != null -> {
                    uiElements.add(
                        UIElement(
                            id = profileElement.Birthday!!.Id,
                            type = ElementType.EDIT_TEXT_CALENDAR,
                            hint = profileElement.Birthday!!.Label,
                            isVisible = !profileElement.Birthday!!.Hide,
                            isEnabled = !profileElement.Birthday!!.Disabled
                        )
                    )
                }
            }
        }
        return uiElements
    }
}