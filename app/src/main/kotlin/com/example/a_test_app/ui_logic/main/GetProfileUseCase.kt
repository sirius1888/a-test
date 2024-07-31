package com.example.a_test_app.ui_logic.main

import com.example.data.ProfileRepository
import com.example.data.model.RootJSON
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): RootJSON? {
        return repository.get()
    }
}