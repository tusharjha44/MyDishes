package com.example.mydishes.model.database

import androidx.room.*
import com.example.mydishes.model.entities.FavDish
import kotlinx.coroutines.flow.Flow

@Dao
interface FavDishDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavDishesDetails(favDish: FavDish)

    @Query("SELECT * FROM FAV_DISHES_TABLE ORDER BY ID")
    fun getAllDishesList() : Flow<List<FavDish>>


    @Update
    suspend fun updateFavDishDetails(favDish: FavDish)

    /**
     * SQLite does not have a boolean data type. Room maps it to an INTEGER column, mapping true to 1 and false to 0.
     */
    @Query("SELECT * FROM FAV_DISHES_TABLE WHERE favorite_dish = 1")
    fun getFavoriteDishesList(): Flow<List<FavDish>>

    @Delete
    suspend fun deleteFavDishDetails(favDish: FavDish)
}