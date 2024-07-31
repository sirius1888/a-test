package com.example.a_test_app.ui_logic.main

import com.example.a_test_app.ui.main.ProfileMapper
import com.example.common.ui.UIElement
import com.example.data.ProfileRepository
import com.example.data.model.RootJSON
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository,
    private val mapper: ProfileMapper
) {
    suspend operator fun invoke(): List<UIElement>? {
        return repository.get()?.let { profile ->
            mapper.convertProfileToUIElements(profile.ProfileElements)
        }
    }
}