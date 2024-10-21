package com.example.a_test_app.ui_logic.main

import android.util.Log
import com.example.a_test_app.ui_logic.main.mapper.ProfileToUIElementsMapper
import com.example.common.ui.UIElement
import com.example.data.ProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository,
    private val mapper: ProfileToUIElementsMapper
) {
    suspend operator fun invoke(): List<UIElement>? {
        return repository.get()?.let { profile ->
            mapper.convertProfileToUIElements(profile.ProfileElements)
        }
    }
}