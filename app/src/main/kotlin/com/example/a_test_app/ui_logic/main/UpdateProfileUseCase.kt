package com.example.a_test_app.ui_logic.main

import com.example.a_test_app.ui_logic.main.mapper.UIElementsToProfileMapper
import com.example.common.ui.UIElement
import com.example.data.ProfileRepository
import com.example.data.model.RootJSON
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: ProfileRepository,
    private val mapper: UIElementsToProfileMapper
) {
    suspend fun update(uiElements: List<UIElement>) {
        val profileElements = mapper.convertUIElementsToProfile(uiElements)
        val updatedProfile = RootJSON(
            TestMode = false,
            EmulationMode = false,
            ProfileElements = profileElements
        )
        repository.update(updatedProfile)
    }
}