package com.example.a_test_app.ui_logic.main.mapper

import com.example.common.ui.ElementType
import com.example.common.ui.UIElement
import com.example.data.model.ProfileElement
import javax.inject.Inject

class ProfileToUIElementsMapper @Inject constructor() {
    fun convertProfileToUIElements(profileElements: List<ProfileElement>): List<UIElement> {
        return profileElements.flatMap { profileElement ->
            mutableListOf<UIElement>().apply {
                profileElement.Column?.let { column ->
                    add(
                        UIElement(
                            id = column.Id,
                            type = ElementType.COLUMN,
                            children = convertProfileToUIElements(column.ProfileElements),
                            isVisible = !column.Hide
                        )
                    )
                }
                profileElement.Row?.let { row ->
                    add(
                        UIElement(
                            id = row.Id,
                            type = ElementType.ROW,
                            children = convertProfileToUIElements(row.ProfileElements),
                            isVisible = !row.Hide
                        )
                    )
                }
                profileElement.Button?.let { button ->
                    add(
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
                    add(
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
                    add(
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
                    add(
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
        }
    }
}