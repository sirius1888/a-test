package com.example.a_test_app.ui.main

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.a_test_app.databinding.ActivityMainBinding
import com.example.common.binding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: ProfileViewModel by viewModels()
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val ui by lazy { MainUI(this, ::buttonActions) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val dynamicLayout: LinearLayout = binding.dynamicLayout

        lifecycleScope.launch {
            viewModel.uiElements.collect { elements ->
                ui.buildUI(dynamicLayout, elements)
            }
        }

        lifecycleScope.launch {
            viewModel.isEditing.collect { isEditing ->
                handleEditingMode(isEditing)
            }
        }
    }

    private fun buttonActions(id: String) {
        when (id) {
            "button_edit_id" -> viewModel.isEditing(true)
            "button_save_id" -> viewModel.saveProfile()
            "button_cancel_id" -> viewModel.isEditing(false)
        }
    }

    private fun handleEditingMode(isEditing: Boolean) {
        ui.buildUI(binding.dynamicLayout, viewModel.uiElements.value)
    }
}