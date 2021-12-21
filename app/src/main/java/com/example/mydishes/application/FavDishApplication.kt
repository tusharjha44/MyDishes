package com.example.mydishes.application

import android.app.Application
import com.example.mydishes.model.database.FavDishRepository
import com.example.mydishes.model.database.FavDishRoomDatabase

class FavDishApplication :Application(){
    private val database by lazy {
        FavDishRoomDatabase.getDatabase(this@FavDishApplication)}
        val repository by lazy { FavDishRepository(database.favDishDao()) }
}