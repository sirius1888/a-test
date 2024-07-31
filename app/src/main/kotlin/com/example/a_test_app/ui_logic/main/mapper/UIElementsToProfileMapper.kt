package com.example.a_test_app.ui_logic.main.mapper

import com.example.common.ui.ElementType
import com.example.common.ui.UIElement
import com.example.data.model.Birthday
import com.example.data.model.Button
import com.example.data.model.Column
import com.example.data.model.Gender
import com.example.data.model.LastName
import com.example.data.model.ProfileElement
import com.example.data.model.Row
import com.example.data.model.createProfileElement
import javax.inject.Inject


class UIElementsToProfileMapper @Inject constructor() {

    fun convertUIElementsToProfile(uiElements: List<UIElement>): List<ProfileElement> {
        val elements = mutableListOf<ProfileElement>()

        uiElements.forEach { element ->
            when (element.type) {
                ElementType.BUTTON -> elements.add(createProfileElement<Button>(element))
                ElementType.EDIT_TEXT -> elements.add(createProfileElement<LastName>(element))
                ElementType.SPINNER -> elements.add(createProfileElement<Gender>(element))
                ElementType.EDIT_TEXT_CALENDAR -> elements.add(
                    createProfileElement<Birthday>(
                        element
                    )
                )

                ElementType.COLUMN -> elements.add(
                    ProfileElement(
                        Column = Column(
                            Id = element.id,
                            Hide = !element.isVisible,
                            ProfileElements = convertUIElementsToProfile(
                                element.children ?: emptyList()
                            )
                        )
                    )
                )

                ElementType.ROW -> elements.add(
                    ProfileElement(
                        Row = Row(
                            Id = element.id,
                            Hide = !element.isVisible,
                            ProfileElements = convertUIElementsToProfile(
                                element.children ?: emptyList()
                            )
                        )
                    )
                )
            }
        }
        return elements
    }
}