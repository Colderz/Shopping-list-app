package pakiet.arkadiuszzimny.shoppinglist.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pakiet.arkadiuszzimny.shoppinglist.data.db.entities.ShoppingItem

@Database(
    entities = [ShoppingItem::class],
    version = 1
)
abstract class ShoppingDatabase: RoomDatabase() {

    abstract fun getShoppingDao(): ShoppingDao

    companion object {
        //Volatile oznacza, że będzie widoczne natychmiast dla innych wątków (zmienna instance)
        @Volatile
        private var instance: ShoppingDatabase? = null
        private var LOCK = Any()

        //funkcja wykonywana za każdym utwoerzeniem instancji ShopingDatabase
        //jeśli instancja to null - wykonujemy synchronized, a w nim tworzymy tę bazę
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance =it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            ShoppingDatabase::class.java, "ShoppingDB.db").build()
    }

}