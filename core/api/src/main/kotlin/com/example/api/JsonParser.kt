package com.example.api

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.io.File
import java.io.IOException
import javax.inject.Inject

public abstract class JSONParser<T> @Inject constructor(
    private val context: Context,
    private val gson: Gson,
    private val fileName: String
) {

    public abstract val clazz: Class<T>

    public fun readFromAssets(): T? {
        return try {
            val json = context.assets.open(fileName).bufferedReader().use { it.readText() }
            gson.fromJson(json, clazz)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    public fun readFromInternalStorage(): T? {
        return try {
            val file = File(context.filesDir, fileName)
            if (file.exists()) {
                val json = file.readText()
                gson.fromJson(json, clazz)
            } else {
                null
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    public fun writeToInternalStorage(data: T) {
        try {
            val json = gson.toJson(data)
            val file = File(context.filesDir, fileName)
            file.writeText(json)
            Log.v("CONSOLE_LOG_JSON", json.toString())
        } catch (ex: IOException) {
            Log.v("CONSOLE_LOG_JSON", ex.message.toString())
            ex.printStackTrace()
        }
    }
}
