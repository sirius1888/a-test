package com.example.data

import android.content.Context
import com.example.api.JSONParser
import com.example.data.model.RootJSON
import com.google.gson.Gson

public class RootJSONParser(
    context: Context,
    gson: Gson,
    fileName: String
) : JSONParser<RootJSON>(context, gson, fileName) {
    override val clazz: Class<RootJSON>
        get() = RootJSON::class.java
}