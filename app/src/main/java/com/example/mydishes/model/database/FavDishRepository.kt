package com.example.mydishes.model.database

import androidx.annotation.WorkerThread
import com.example.mydishes.model.entities.FavDish
import kotlinx.coroutines.flow.Flow

class FavDishRepository(private var favDishDao: FavDishDao) {
    @WorkerThread
    suspend fun insertFavDishData(favDish: FavDish){
        favDishDao.insertFavDishesDetails(favDish)
    }

    val allDishesList: Flow<List<FavDish>> = favDishDao.getAllDishesList()

}