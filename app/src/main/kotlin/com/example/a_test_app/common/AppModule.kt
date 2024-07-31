package com.example.a_test_app.common

import android.content.Context
import com.example.api.JSONParser
import com.example.data.RootJSONParser
import com.example.data.model.RootJSON
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRootJSONParser(@ApplicationContext context: Context): RootJSONParser {
        return RootJSONParser(
            context = context,
            gson = Gson(),
            fileName = "MobileEditProfile.json"
        )
    }
}