package com.example.a_test_app.ui.main

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.a_test_app.databinding.ActivityMainBinding
import com.example.common.binding.viewBinding
import com.example.common.view.setVisibility
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

        val dynamicLayout: LinearLayout = binding.dynamicLayout

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->

                binding.progressBar.setVisibility(state.isLoading)
                binding.dynamicLayout.setVisibility(!state.isLoading)

                if (state.error != null) {
                    Toast.makeText(
                        applicationContext,
                        state.error,
                        Toast.LENGTH_LONG
                    ).show()
                }
                ui.buildUI(dynamicLayout, state.uiElements)
            }
        }
    }

    private fun buttonActions(id: String) {
        when (id) {
            "button_save_id" -> viewModel.updateProfile()
            "button_edit_id" -> viewModel.onEditButtonClick()
            "button_cancel_id" -> viewModel.onCancelButtonClick()
        }
    }
}

