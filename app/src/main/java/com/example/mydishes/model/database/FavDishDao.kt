package com.example.mydishes.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mydishes.model.entities.FavDish
import kotlinx.coroutines.flow.Flow

@Dao
interface FavDishDao {

    @Insert
    suspend fun insertFavDishesDetails(favDish: FavDish)

    @Query("SELECT * FROM FAV_DISHES_TABLE ORDER BY ID")
    fun getAllDishesList() : Flow<List<FavDish>>
}