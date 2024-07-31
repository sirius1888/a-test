package com.example.common.view

import android.R
import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.common.ui.UIElement
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.reflect.KProperty0

public fun createButton(
    context: Context,
    element: UIElement,
    action: (String) -> Unit
): MaterialButton = MaterialButton(context).apply {
    text = element.label
    setOnClickListener {
        action(element.id)
    }
}

public fun createEditText(context: Context, element: UIElement): EditText =
    EditText(context).apply {
        setText(element.field)
        hint = element.hint
        isEnabled = element.isEnabled
    }

public fun createEditTextWithAction(context: Context, element: UIElement): EditText =
    createEditText(context, element).apply {
        setOnClickListener {
            if (isEnabled) {
                showDatePickerDialog(context) {
                    setText(it)
                }
            }
        }
    }

private fun showDatePickerDialog(context: Context, onDateSet: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = formatDate(dayOfMonth, month, year)
            onDateSet(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.show()
}

private fun formatDate(day: Int, month: Int, year: Int): String {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, day)
        set(Calendar.MONTH, month)
        set(Calendar.YEAR, year)
    }

    val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
    return dateFormat.format(calendar.time)
}


public fun createSpinner(context: Context, element: UIElement): Spinner = Spinner(context).apply {
    isEnabled = element.isEnabled
    val adapter = ArrayAdapter(
        context, R.layout.simple_spinner_item, element.options ?: emptyList()
    )
    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
    this.adapter = adapter
}
