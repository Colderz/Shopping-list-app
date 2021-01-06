package pakiet.arkadiuszzimny.shoppinglist.ui.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import pakiet.arkadiuszzimny.shoppinglist.R
import pakiet.arkadiuszzimny.shoppinglist.data.db.ShoppingDatabase
import pakiet.arkadiuszzimny.shoppinglist.data.repositories.ShoppingRepository

class ShoppingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        val database = ShoppingDatabase(this)
        val repository = ShoppingRepository(database)
        val factory = ShoppingViewModelFactory(repository)

        val viewModel = ViewModelProviders.of(this).get(ShoppingViewModel::class.java)
    }
}