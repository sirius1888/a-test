package com.example.data

import com.example.data.model.RootJSON
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

public class ProfileRepository @Inject constructor(
    private val jsonParser: RootJSONParser
) {
    public suspend fun get(): RootJSON? {
        return withContext(Dispatchers.IO) {
            jsonParser.readFromAssets()
        }
    }

    public suspend fun update(data: RootJSON) {
        return withContext(Dispatchers.IO) {
            jsonParser.writeToInternalStorage(data)
        }
    }
}
