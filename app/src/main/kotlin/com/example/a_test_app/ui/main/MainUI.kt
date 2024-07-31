package com.example.a_test_app.ui.main

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.example.common.ui.ElementType
import com.example.common.ui.UIElement
import com.example.common.view.createButton
import com.example.common.view.createEditText
import com.example.common.view.createEditTextWithAction
import com.example.common.view.createSpinner

class MainUI(private val context: Context, private val action: (String) -> Unit) {

    internal fun buildUI(layout: LinearLayout, elements: List<UIElement>, ) {
        layout.removeAllViews()

        for (element in elements) {
            val view = when (element.type) {
                ElementType.BUTTON -> createButton(context, element, action)
                ElementType.EDIT_TEXT -> createEditText(context, element)
                ElementType.SPINNER -> createSpinner(context, element)
                ElementType.EDIT_TEXT_CALENDAR -> createEditTextWithAction(context, element)
            }

            if (view != null) {
                view.isEnabled = element.isEnabled
                view.visibility = if (element.isVisible) View.VISIBLE else View.GONE
                layout.addView(view)
            }
        }
    }
}
