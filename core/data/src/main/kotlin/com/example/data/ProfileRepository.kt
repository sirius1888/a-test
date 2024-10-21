package com.example.data

import android.util.Log
import com.example.data.model.RootJSON
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

public class ProfileRepository @Inject constructor(
    private val jsonParser: RootJSONParser
) {
    @OptIn(ExperimentalStdlibApi::class)
    public suspend fun get(): RootJSON? {
        return withContext(Dispatchers.IO) {
            val currentThread = Thread.currentThread().name
            val currentDispatcher = coroutineContext[CoroutineDispatcher]?.toString() ?: "Unknown Dispatcher"

            Log.d("ProfileDataLoader", "get Current thread: $currentThread")
            Log.d("ProfileDataLoader", "get Current dispatcher: $currentDispatcher")

            jsonParser.readFromAssets()
        }
    }

    public suspend fun update(data: RootJSON) {
        return withContext(Dispatchers.IO) {
            jsonParser.writeToInternalStorage(data)
        }
    }
}
