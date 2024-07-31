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
    internal fun buildUI(layout: LinearLayout, elements: List<UIElement>) {
        layout.removeAllViews()
        elements.forEach { element ->
            val view = createViewForElement(element)
            view.isEnabled = element.isEnabled
            view.visibility = if (element.isVisible) View.VISIBLE else View.GONE
            layout.addView(view)
        }
    }

    private fun createViewForElement(element: UIElement): View {
        return when (element.type) {
            ElementType.BUTTON -> createButton(context, element, action)
            ElementType.EDIT_TEXT -> createEditText(context, element)
            ElementType.SPINNER -> createSpinner(context, element)
            ElementType.EDIT_TEXT_CALENDAR -> createEditTextWithAction(context, element)
            ElementType.COLUMN -> createColumnLayout(element)
            ElementType.ROW -> createRowLayout(element)
        }
    }

    private fun createColumnLayout(element: UIElement): LinearLayout {
        val columnLayout = LinearLayout(context).apply { orientation = LinearLayout.VERTICAL }
        element.children?.let { buildUI(columnLayout, it) }
        return columnLayout
    }

    private fun createRowLayout(element: UIElement): LinearLayout {
        val rowLayout = LinearLayout(context).apply { orientation = LinearLayout.HORIZONTAL }
        element.children?.let { buildUI(rowLayout, it) }
        return rowLayout
    }


}
