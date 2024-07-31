package com.example.a_test_app.ui.main

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.a_test_app.databinding.ActivityMainBinding
import com.example.a_test_app.ui_logic.main.MainUIState
import com.example.common.binding.viewBinding
import com.example.common.ui.UIElement
import com.example.common.view.gone
import com.example.common.view.setVisibility
import com.example.common.view.visible
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val ui by lazy { MainUI(this, ::buttonActions) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when {
                    state.isLoading -> showLoading()
                    state.error != null -> showError(state.error)
                    else -> showContent(state.uiElements)
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visible()
        binding.dynamicLayout.gone()
    }

    private fun showError(error: String) {
        binding.progressBar.gone()
        binding.dynamicLayout.gone()
        Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
    }

    private fun showContent(uiElements: List<UIElement>) {
        binding.progressBar.gone()
        binding.dynamicLayout.visible()
        ui.buildUI(binding.dynamicLayout, uiElements)
    }

    private fun buttonActions(id: String) {
        when (id) {
            "button_save_id" -> viewModel.updateProfile()
            "button_edit_id" -> viewModel.onEditButtonClick()
            "button_cancel_id" -> viewModel.onCancelButtonClick()
        }
    }
}