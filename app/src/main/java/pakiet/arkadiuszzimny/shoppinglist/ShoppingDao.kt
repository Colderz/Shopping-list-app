package pakiet.arkadiuszzimny.shoppinglist

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingDao {

    //oznaczenie metod jako suspend (zawieszone) daje nam pewność, że nie są wykonywane w głównym wątku

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: ShoppingItem)

    @Delete
    suspend fun delete(item: ShoppingItem)

    @Query("SELECT * FROM shopping_items")
    suspend fun getAllShoppingItems() : LiveData<List<ShoppingItem>>

}